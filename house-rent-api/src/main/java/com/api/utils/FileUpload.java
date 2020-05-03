package com.api.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class FileUpload {
    public String uploadFile(MultipartFile file,int ownerId){
        try {
            final String uploadRoot="D:/houseRent/static/images";
            final String FILE_SPLIT="/";
            long timestamp=System.currentTimeMillis();
            String originalName = file.getOriginalFilename();
            StringBuffer fileName = new StringBuffer();
            fileName.append(uploadRoot).append(FILE_SPLIT); // D:/houseRent/
            fileName.append(ownerId).append(FILE_SPLIT);
            fileName.append(timestamp).append(FILE_SPLIT);
            fileName.append(originalName);
            String outputDir = fileName.toString();
            StringBuffer imageUrl = new StringBuffer("/static/images/");
            //相对路径
            imageUrl.append(ownerId).append(FILE_SPLIT).append(timestamp).append(FILE_SPLIT).append(originalName);
            File importFile = new File(fileName.toString());
            System.out.println("Upload File "+originalName+" to "+outputDir);
            System.out.println("相对路径："+imageUrl);
            FileUtils.copyInputStreamToFile(file.getInputStream(), importFile);
            return imageUrl.toString();
        } catch (Exception e) {
            System.out.println("保存文件失败"+e.getMessage());
        }
        return null;
    }
}
