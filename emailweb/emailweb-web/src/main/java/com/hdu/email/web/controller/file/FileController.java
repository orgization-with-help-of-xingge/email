package com.hdu.email.web.controller.file;

import com.email.tools.FtpUtil;
import com.email.tools.IDUtils;
import com.hdu.email.common.util.transfer.BaseReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

/**
 * 功能描述: 文件上传controller
 * @Author: sixl
 * @Date: 2019/11/23 15:24
 */
@CrossOrigin
@RestController
@RequestMapping("/emailfile")
@Slf4j
public class FileController {
    private String ip;
    private String port;
    private String username;
    private String password;
    private String basePath;

    private String headPath = "head";

    private String filePath = "emailfile";

//    private String


    @RequestMapping("/upload")
    public BaseReturnResult  headUpload(HttpServletRequest request) {
        getProperties();
        BaseReturnResult result = BaseReturnResult.getFailResult();
        FtpUtil ftpUtil = new FtpUtil();
        String fileurl = "";
        Boolean flag=true;
        try {
            //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
            CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            //检查form中是否有enctype="multipart/form-data"
            if(multipartResolver.isMultipart(request))
            {
                //将request变成多部分request
                MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
                //获取multiRequest 中所有的文件名
                Iterator iter=multiRequest.getFileNames();
                while(iter.hasNext())
                {
                    //一次遍历所有文件
                    MultipartFile file=multiRequest.getFile(iter.next().toString());
                    if(file!=null)
                    {
                        InputStream inputStream = file.getInputStream();
//                        String originalFilename = file.getOriginalFilename();
//                        String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                        String filename = IDUtils.genImageName() + ".png";
                        flag = FtpUtil.uploadFile(ip, Integer.parseInt(port), username, password, basePath, headPath, filename, inputStream);
                        fileurl = "http://"+ip+"/"+ headPath +"/"+filename;
                        if (!flag){
                            break;
                        }
                    }
                }
            }
            if (flag){
                result.setObject(fileurl);
                result.setWhenSuccess();
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }


    @RequestMapping("/uploadfile")
    public BaseReturnResult  fileUpload(HttpServletRequest request) {
        getProperties();
        BaseReturnResult result = BaseReturnResult.getFailResult();
        FtpUtil ftpUtil = new FtpUtil();
        String fileurl = "";
        Boolean flag=true;
        try {
            //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
            CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            //检查form中是否有enctype="multipart/form-data"
            if(multipartResolver.isMultipart(request))
            {
                //将request变成多部分request
                MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
                //获取multiRequest 中所有的文件名
                Iterator iter=multiRequest.getFileNames();
                while(iter.hasNext())
                {
                    //一次遍历所有文件
                    MultipartFile file=multiRequest.getFile(iter.next().toString());
                    if(file!=null)
                    {
                        InputStream inputStream = file.getInputStream();
                        String originalFilename = file.getOriginalFilename();
                        String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                        String filename = originalFilename;
//                        String filename = IDUtils.genImageName() + ".png";
                        flag = FtpUtil.uploadFile(ip, Integer.parseInt(port), username, password, basePath, filePath, filename, inputStream);
                        fileurl = "http://"+ip+"/"+ filePath +"/"+filename;
                        if (!flag){
                            break;
                        }
                    }
                }
            }
            if (flag){
                result.setObject(fileurl);
                result.setWhenSuccess();
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }


    //获取配置文件属性
    /**
     * 使用java.util.Properties读取
     * @author yan
     */
    public void getProperties(){
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("/file.properties");
            Properties config = new Properties();
            config.load(inputStream);
            ip=config.getProperty("ftp.ip");
            port=config.getProperty("ftp.port");
            username=config.getProperty("ftp.username");
            password=config.getProperty("ftp.password");
            basePath=config.getProperty("ftp.basePath");
            System.out.println("name:" + config.getProperty("name"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
