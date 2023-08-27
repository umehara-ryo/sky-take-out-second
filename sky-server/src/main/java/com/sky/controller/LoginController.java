package com.sky.controller;

import com.sky.entity.Employee;
import com.sky.entity.LoginUser;
import com.sky.result.Result;
import com.sky.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "ログインに関するインタフェース")
@RequestMapping
@Slf4j
@CrossOrigin
public class LoginController {
    @Autowired
    private LoginService loginService;

    //従業員ログイン
//    @ApiOperation("従業員ログイン")
//    @GetMapping("/login")
//    public Result<List<LoginUser>> login(LoginUser loginUser) {
//        log.info("：{}", loginUser.getAccountId());
//        List<LoginUser>  loginUserList = loginService.login(loginUser);
//        //ユーザーネームとパスワードでidを獲得
//        return Result.success(loginUserList);
//    }
    @ApiOperation("従業員ログイン")
    @PostMapping("/employee/login")
    public Result<List<LoginUser>> loginPost(@RequestBody LoginUser loginUser) {
        log.info("post{}", loginUser.getAccountId());
        List<LoginUser>  loginUserList = loginService.login(loginUser);
        //ユーザーネームとパスワードでidを獲得
        return Result.success(loginUserList);
    }





}
