package com.sky.exception;

//アカウントがロックアウトされているという例外

public class AccountLockedException extends BaseException {

    public AccountLockedException() {
    }

    public AccountLockedException(String msg) {
        super(msg);
    }

}
