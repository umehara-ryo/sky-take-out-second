package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user/addressBook")
@RestController
@Slf4j
@Api(tags = "アドレス帳に関わるインタフェース")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    @ApiOperation("アドレス帳に新規アドレス追加")
    public Result add(@Autowired AddressBook addressBook){
        log.info("アドレス帳に新規アドレス追加{}",addressBook);
        addressBookService.add(addressBook);

        return Result.success();
    }




}
