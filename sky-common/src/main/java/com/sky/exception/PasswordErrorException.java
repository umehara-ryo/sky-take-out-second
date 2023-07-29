package com.sky.exception;

//passwordが違う時の例外

public class PasswordErrorException extends BaseException {


    public PasswordErrorException(){}

    public PasswordErrorException(String msg) {

        super(msg);

    }


}
