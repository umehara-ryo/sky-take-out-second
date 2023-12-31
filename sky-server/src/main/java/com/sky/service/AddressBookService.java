package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    void add(AddressBook addressBook);

    List<AddressBook> list();

    AddressBook getDefault();

    AddressBook getById(Long id);

    void setDefault(Long id);

    void update(AddressBook addressBook);

    void delete(Long id);
}
