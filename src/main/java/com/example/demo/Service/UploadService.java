package com.example.demo.Service;


import jakarta.servlet.ServletContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class UploadService {
        private final ServletContext servletContext;

    public UploadService(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    public String handleSaveUploadFile(MultipartFile file, String targetFolder) {

        if (file.isEmpty()) {
            return "";
        }

        //lưu file tại images
        String rootPath = this.servletContext.getRealPath("/resources/images");
        String finalName = "";
        try {
            //lấy hình ảnh dưới dạng binary
            byte[] bytes = file.getBytes();
            //trỏ tới avatar
            File dir = new File(rootPath + File.separator + targetFolder);
            // nếu k tồn tại sẽ tạo mới nó
            if (!dir.exists())
                dir.mkdirs();

            finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            // Create the file on server: quy định tên file chúng ta lưu
            File serverFile = new File(dir.getAbsolutePath() + File.separator +finalName);
            //Truyền vào file muon luu
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return targetFolder+"/"+finalName;
    }
}
