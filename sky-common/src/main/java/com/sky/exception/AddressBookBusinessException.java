package com.sky.exception;

//アドレスビジネスにおける例外

public class AddressBookBusinessException extends BaseException {

    public AddressBookBusinessException(String msg) {
        super(msg);
    }

}
