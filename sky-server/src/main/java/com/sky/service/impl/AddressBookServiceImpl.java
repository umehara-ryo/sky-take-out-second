package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    public void add(AddressBook addressBook) {
        //userIdを挿入
        addressBook.setUserId(BaseContext.getCurrentId());

        //アドレス帳表に挿入
        addressBookMapper.add(addressBook);

    }

    @Override
    public List<AddressBook> list() {
        return  addressBookMapper.list();
    }
}
