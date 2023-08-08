package com.sky.exception;

//注文している時の例外

public class OrderBusinessException extends BaseException {

    public OrderBusinessException(String msg) {
        super(msg);
    }

}
