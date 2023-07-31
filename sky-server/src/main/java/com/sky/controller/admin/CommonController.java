package com.sky.controller.admin;

import com.sky.properties.AliOssProperties;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Api(tags = "共用インタフェース")
@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssProperties aliOssProperties;

    @PostMapping("/upload")
    @ApiOperation("ファイルアップロード")
    public Result<String> upload(MultipartFile file) throws IOException {
        log.info("データアップロード中{}",file);

        //1.ファイルの拡張子を獲得
        String filename = file.getOriginalFilename();
        int index = filename.lastIndexOf(".");
        String format = filename.substring(index);

        //2.ファイルのバイト配列を獲得
        byte[] fileBytes = file.getBytes();

        //3.UUIDでランダムの新しいファイル名を与える
        UUID uuid = UUID.randomUUID();

        String newName = uuid + format;

        //4.ossでurlをゲット
        AliOssUtil aliOssUtil = new AliOssUtil();
        aliOssUtil.setAccessKeyId(aliOssProperties.getAccessKeyId());
        aliOssUtil.setAccessKeySecret(aliOssProperties.getAccessKeySecret());
        aliOssUtil.setEndpoint(aliOssProperties.getEndpoint());
        aliOssUtil.setBucketName(aliOssProperties.getBucketName());

        String url = aliOssUtil.upload(fileBytes, newName);
        //5.返す

        return Result.success(url);
    }
}
