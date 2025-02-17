package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
@Setter
@ConfigurationProperties(prefix = "server")
public class CommonController {

    private String port;
    private String host;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file.getOriginalFilename());
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectFileName = UUID.randomUUID().toString() + extension;

            String dir = System.getProperty("user.dir");
            File filepath = new File(dir + "/files/" + objectFileName);
            //判断文件的父文件夹是否存在 如果不存在 则创建
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }

            file.transferTo(filepath);

            //返回文件访问路径
            //这里是在配置类中配置的访问路径，访问项目中的文件需要ip和端口号才能访问到具体的程序
            String url = "http://" + host + ":" + port + "/image/" + objectFileName;
            return Result.success(url);
        } catch (IOException e) {
            log.error("文件上传失败：{}", e.getMessage());
        }

        return Result.error("文件上传失败");
    }

    @PostMapping("/send")
    @ApiOperation("发送数据")
    public Result<String> send(String text) {
        log.info(text);
        String content="返回";
        return Result.success(content);
    }

}
