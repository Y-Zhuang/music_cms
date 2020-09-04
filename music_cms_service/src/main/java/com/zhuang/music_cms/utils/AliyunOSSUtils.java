package com.zhuang.music_cms.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.zhuang.music_cms.config.AliyunOSSConfig;
import com.zhuang.music_cms.interceptor.PutObjectProgressListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Package     : com.zhuang.music_cms.utils
 * @ClassName   : AliyunOSSUtils 
 * @Description : 阿里云oss工具类
 * @Author      : Zhuang
 * @Date        : 2020-04-24 21:03
 */
public class AliyunOSSUtils {

    @Autowired
    private AliyunOSSConfig aliyunOSSConfig;

    public String uploadFile(MultipartFile file, String bucketName, String folderPath) throws Exception {
        OSSClient ossClient = new OSSClient(aliyunOSSConfig.getEndpoint(), aliyunOSSConfig.getAccessKeyId(), aliyunOSSConfig.getAccessKeySecret());
        try {
            String suffix = "";
            if (file.getOriginalFilename().contains(".")) {
                suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
            InputStream inputStream = file.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            fileName = StringUtils.isNotBlank(folderPath) ? folderPath + "/" + fileName : fileName;
            PutObjectResult result = ossClient.putObject(bucketName, fileName, inputStream, objectMetadata);
            return fileName;
        } catch (Exception e) {
            ossClient.shutdown();
            throw new Exception(e);
        } finally {
            ossClient.shutdown();
        }
    }

    public List<String> uploadFile(MultipartFile[] files, String bucketName, String folderPath) throws Exception {
        OSSClient ossClient = new OSSClient(aliyunOSSConfig.getEndpoint(), aliyunOSSConfig.getAccessKeyId(), aliyunOSSConfig.getAccessKeySecret());
        try {
            List<String> fileIds = new ArrayList<>();
            for (MultipartFile file : files) {
                String suffix = "";
                if (file.getOriginalFilename().contains(".")) {
                    suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                }
                String fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
                InputStream inputStream = file.getInputStream();
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(inputStream.available());
                objectMetadata.setCacheControl("no-cache");
                objectMetadata.setHeader("Pragma", "no-cache");
                objectMetadata.setContentType(file.getContentType());
                objectMetadata.setContentDisposition("inline;filename=" + fileName);
                fileName = StringUtils.isNotBlank(folderPath) ? folderPath + "/" + fileName : fileName;
                PutObjectResult result = ossClient.putObject(bucketName, fileName, inputStream, objectMetadata);
                fileIds.add(fileName);
            }
            return fileIds;
        } catch (Exception e) {
        ossClient.shutdown();
        throw new Exception(e);
        } finally {
        ossClient.shutdown();
        }
    }

    public String uploadFileProgress(MultipartFile file, String bucketName, String folderPath, HttpSession session) throws Exception {
        OSSClient ossClient = new OSSClient(aliyunOSSConfig.getEndpoint(), aliyunOSSConfig.getAccessKeyId(), aliyunOSSConfig.getAccessKeySecret());
        String suffix = "";
        if (file.getOriginalFilename().contains(".")) {
            suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        }
        final File excelFile = File.createTempFile(UUID.randomUUID().toString(), suffix);
        file.transferTo(excelFile);
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
        fileName = StringUtils.isNotBlank(folderPath) ? folderPath + "/" + fileName : fileName;
        try{
            PutObjectResult putObjectResult = ossClient.putObject(new PutObjectRequest(bucketName, fileName, excelFile).
                    <PutObjectRequest>withProgressListener(new PutObjectProgressListener(session)));
            return fileName;
        } catch (Exception e) {
            deleteFile(excelFile);
            ossClient.shutdown();
            throw new Exception(e);
        } finally {
            deleteFile(excelFile);
            ossClient.shutdown();
        }
    }

    public void deleteFile(String fileName, String bucketName) throws Exception {
        OSSClient ossClient = new OSSClient(aliyunOSSConfig.getEndpoint(), aliyunOSSConfig.getAccessKeyId(), aliyunOSSConfig.getAccessKeySecret());
        ossClient.deleteObject(bucketName, fileName);
        ossClient.shutdown();
    }

    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.deleteOnExit();
            }
        }
    }

}
