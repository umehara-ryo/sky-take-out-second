package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.LoginFailedException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Profile("dev")
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {

        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        //使用md5加密为新的密码

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定)
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus().equals(StatusConstant.DISABLE)) {
            //账号被锁定
            //TODO 如何设置登录三次失败后锁定账号（可否捕获异常并自增实现？）
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    @Override
    public void save(EmployeeDTO employeeDTO) {
        //1.同じユーザー名がテーブルに既に存在するかどうかを調べる
        Employee employee = employeeMapper.getByUsername(employeeDTO.getUsername());
        if (employee != null) {
            throw new LoginFailedException(employee.getUsername() + MessageConstant.ALREADY_EXISTS_ERROR);
            //存在ならそれきり
        }

        //2.dtoをempオブジェクトにラップする
        employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        //3.パスワードを暗号化する
        String password = DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes());
        //ハードコーディング用いない　デフォルトのパスワードは123456
        employee.setPassword(password);

        //TODO 后续AOP实现
        //4.作成時間と更新時間、作成者と更新者をセットする
        //TODO 後で、スレッドから　id　を取り出す

        Long currentId = BaseContext.getCurrentId();

//        employee.setCreateUser(currentId);
//        employee.setUpdateUser(currentId);
//
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());


        //5.动态sql插入数据,并开启主键返回
        employeeMapper.insert(employee);


    }

    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        //1.启动pagehelper
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());

        //2.查询表返回Page(List)集合
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);

        //3、封装pageResult
        long total = page.getTotal();
        List<Employee> result = page.getResult();


        PageResult pageResult = new PageResult(total, result);
        return pageResult;
    }

    @Override
    public void switchOnOff(Integer status,Long id) {

        Employee employee = Employee.builder()
                .status(status).id(id)
                .updateUser(BaseContext.getCurrentId())
                .updateTime(LocalDateTime.now())
                .build();
        employeeMapper.update(employee);
    }

    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.selectById(id);
        return employee;
    }

    @Override
    public void update(EmployeeDTO employeeDTO) {

        //1.empオブジェクトに値を代入
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);

        //2.更新時刻と更新者を代入
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(BaseContext.getCurrentId());

        //3．情報を更新する
        employeeMapper.update(employee);


    }

    @Override
    public void editPassword(PasswordEditDTO passwordEditDTO) {

        //0.スレッドからidをとりだす
        Long empId = BaseContext.getCurrentId();

        //1.旧パスワードの暗号化と比較
        Employee employee = employeeMapper.selectById(empId);

        String oldPassword = DigestUtils.md5DigestAsHex(passwordEditDTO.getOldPassword().getBytes());
        if(!oldPassword.equals(employee.getPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //2.ニューパスを暗号化
        String newPassword = DigestUtils.md5DigestAsHex(passwordEditDTO.getNewPassword().getBytes());

        //3.empオブジェクトを作成し、値代入、更新時刻と更新者を代入
        employee.setPassword(newPassword);
//        employee.setUpdateUser(BaseContext.getCurrentId());
//        employee.setUpdateTime(LocalDateTime.now());
        //4.変更
        employeeMapper.update(employee);
    }


}
