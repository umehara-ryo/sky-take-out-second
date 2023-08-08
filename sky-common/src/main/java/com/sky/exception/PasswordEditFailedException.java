package com.sky.exception;

//パスワードの変更に失敗の例外

public class PasswordEditFailedException extends BaseException{

    public PasswordEditFailedException(String msg){
        super(msg);
    }

}
