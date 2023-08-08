package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user/addressBook")
@RestController
@Slf4j
@Api(tags = "アドレス帳に関わるインタフェース")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    @ApiOperation("アドレス帳に新規アドレス追加")
    public Result add(@RequestBody AddressBook addressBook){
        log.info("アドレス帳に新規アドレス追加{}",addressBook);
        addressBookService.add(addressBook);

        return Result.success();
    }


    @GetMapping("/list")
    @ApiOperation("アドレス帳で検索")
    public Result<List<AddressBook>> list(){
        log.info("アドレス帳で検索");
        List<AddressBook> list = addressBookService.list();

        return Result.success(list);
    }

    @GetMapping("/default")
    @ApiOperation("アドレス帳からデフォルトアドレスを検索")
    public Result<AddressBook> getDefault(){
        log.info("アドレス帳からデフォルトアドレスを検索");
        AddressBook addressBook= addressBookService.getDefault();

        return Result.success(addressBook);
    }

    @GetMapping("/{id}")
    @ApiOperation("アドレス帳からidで検索")
    public Result<AddressBook> getById(@PathVariable Long id){
        log.info("アドレス帳からidで検索{}",id);
        AddressBook addressBook= addressBookService.getById(id);

        return Result.success(addressBook);
    }

    @PutMapping("/default")
    @ApiOperation("アドレスをデフォルトに設置する")
    public Result setDefault(@RequestBody Long id){
        log.info("アドレスをデフォルトに設置する{}",id);
        addressBookService.setDefault(id);

        return Result.success();
    }

    @PutMapping
    @ApiOperation("アドレス情報の変更")
    public Result update(@RequestBody AddressBook addressBook){
        log.info("アドレス情報の変更{}",addressBook);
        addressBookService.update(addressBook);

        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("アドレスの削除")
    public Result delete(Long id){
        log.info("アドレスの削除{}",id);
        addressBookService.delete(id);

        return Result.success();
    }





}
