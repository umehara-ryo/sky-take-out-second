package com.sky.exception;


//ログインに失敗したという例外

public class LoginFailedException extends BaseException{
    public LoginFailedException(String msg){
        super(msg);
    }
}
