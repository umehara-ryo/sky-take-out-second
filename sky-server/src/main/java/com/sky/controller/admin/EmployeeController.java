package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@Api(tags = "従業員に関するインタフェース")
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

   //従業員ログイン
    @ApiOperation("従業員ログイン")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody/*jsonなので*/ EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);
        //ユーザーネームとパスワードでidを獲得

        //ログインすると、jwtトークン
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("従業員ログアウト")
    public Result<String> logout() {
        return Result.success();
    }


    @PostMapping
    @ApiOperation("従業員の追加")
    public Result save(@RequestBody EmployeeDTO employeeDTO){
        log.info("従業員の追加{}",employeeDTO);
        employeeService.save(employeeDTO);

        return Result.success();

    }

    @GetMapping("/page")
    @ApiOperation("ページ別でクエリ")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("ページ別でクエリ,{}",employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);

        return  Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("スイッチオンオフ")
    public Result switchOnOff(@PathVariable Integer status,Long id){
        log.info("スイッチオンオフ{},{}",status,id);

        employeeService.switchOnOff(status,id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("idで調べる")
    public Result<Employee> getById(@PathVariable Long id){
        log.info("idで調べる");
        Employee employee = employeeService.getById(id);

        return Result.success(employee);
    }

    @PutMapping
    @ApiOperation("従業員情報更新")
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        log.info("従業員情報更新");
       employeeService.update(employeeDTO);

        return Result.success();
    }

    @PutMapping("editPassword")
    @ApiOperation("パスワード変更")
    public Result editPassword(@RequestBody PasswordEditDTO passwordEditDTO){
        log.info("パスワード変更");
        employeeService.editPassword(passwordEditDTO);

        return Result.success();
    }



}
