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
        //アドレス帳から全てのデータを取り出す
        return  addressBookMapper.list();
    }

    @Override
    public AddressBook getDefault() {
        //アドレス帳からデフォルトアドレスを取り出す
        return addressBookMapper.getDefault();
    }

    @Override
    public AddressBook getById(Long id) {
        //idでアドレスを探し出す
        return addressBookMapper.getById(id);
    }

    @Override
    public void setDefault(Long id) {
        //idでdefaultに設定する
        addressBookMapper.setDefault(id);

    }
}
