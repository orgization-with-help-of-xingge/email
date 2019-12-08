package com.hdu.email.web.controller.file;

import com.emailweb.util.FtpUtil;
import com.emailweb.util.IDUtils;
import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.emailservice.dto.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    private String emailfilePath;

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
                        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                        String filename = IDUtils.genImageName()+suffix;
                        flag = FtpUtil.uploadFile(ip, Integer.parseInt(port), username, password, basePath, filePath, filename, inputStream);
//                        fileurl = "http://"+ip+"/"+ filePath +"/"+filename;
                        fileurl = filename;
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


    @GetMapping(value = "/downloadFile")
    private void download(FileDto fileDto, HttpServletResponse response){
        //获取属性
        getProperties();
        InputStream inputStream = null;
        OutputStream os = null;
        String filePath=null;
        long downloadedLength = 0l;
        try {
            String localPath = System.getProperty("user.dir");
            String ftpfilename = fileDto.getFtpfilename();
            FtpUtil.downloadFile(ip,Integer.parseInt(port),username,password,emailfilePath,ftpfilename,localPath);
            filePath=localPath+ File.separator+ftpfilename;
            //设置响应头和客户端保存文件名
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileDto.getFilename());
//            response.setCharacterEncoding("utf-8");
//            response.setContentType("multipart/octet-stream");
//            response.setHeader("Content-Disposition", "attachment;fileName=" + ftpfilename);
            //用于记录以完成的下载的数据量，单位是byte

            //打开本地文件流
            inputStream = new FileInputStream(filePath);
            //激活下载操作
            os = response.getOutputStream();
            //循环写入输出流
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
                downloadedLength += b.length;
            }

        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            // 这里主要关闭。
            if (os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (filePath != null){
                //删除临时文件夹
                File dir = new File(filePath);
                dir.delete();
            }
        }

    }


    @GetMapping(value = "/downloadFile1")
    private void download1(String filename, HttpServletResponse response){
        //获取属性
        getProperties();
        InputStream inputStream = null;
        OutputStream os = null;
        String filePath=null;
        long downloadedLength = 0l;
        try {
            String localPath = System.getProperty("user.dir");
            FtpUtil.downloadFile(ip,Integer.parseInt(port),username,password,emailfilePath,filename,localPath);
            filePath=localPath+ File.separator+filename;
            //设置响应头和客户端保存文件名
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
//            response.setCharacterEncoding("utf-8");
//            response.setContentType("multipart/octet-stream");
//            response.setHeader("Content-Disposition", "attachment;fileName=" + ftpfilename);
            //用于记录以完成的下载的数据量，单位是byte

            //打开本地文件流
            inputStream = new BufferedInputStream(new FileInputStream(filePath));
            //激活下载操作
            os = response.getOutputStream();
            //循环写入输出流
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
                downloadedLength += b.length;
            }

        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            // 这里主要关闭。
            if (os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (filePath != null){
                //删除临时文件夹
                File dir = new File(filePath);
                dir.delete();
            }
        }

    }

//    @RequestMapping(value="/download",method = RequestMethod.POST)
//    public ResponseEntity<byte[]> download(HttpServletRequest request, FileDto fileDto) throws Exception {
////		//下载文件路径
////		String path = request.getServletContext().getRealPath("/images/");
//        //这是是绝对路径
//        getProperties();
//        String localPath = System.getProperty("user.dir");
//        String ftpfilename = fileDto.getFtpfilename();
//        FtpUtil.downloadFile(ip,Integer.parseInt(port),username,password,emailfilePath,ftpfilename,localPath);
//        String path=localPath+ File.separator+ftpfilename;
//
//        File file = new File(path);
//        HttpHeaders headers = new HttpHeaders();
//        //下载显示的文件名，解决中文名称乱码问题
//        String downloadFileName = new String(ftpfilename.getBytes("UTF-8"),"iso-8859-1");
//        //通知浏览器以attachment（下载方式）打开图片
//        headers.setContentDispositionFormData("attachment", downloadFileName);
//        //application/octet-stream:二进制流数据（最常见的文件下载）
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        //201 HttpStatus.CREATED
//        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
//    }


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
            emailfilePath=config.getProperty("ftp.emailfile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
