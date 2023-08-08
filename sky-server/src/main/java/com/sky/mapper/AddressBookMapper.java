package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    void add(AddressBook addressBook);

    List<AddressBook> list();

    AddressBook getDefault();

    AddressBook getById(Long id);

    void setDefault(Long id);

    void update(AddressBook addressBook);

    void delete(Long id);

}
