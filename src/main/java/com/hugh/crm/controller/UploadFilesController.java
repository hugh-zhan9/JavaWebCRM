package com.hugh.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@Controller
public class UploadFilesController{
    @RequestMapping("upload")
    public String uploadFiles(HttpServletRequest request, List<MultipartFile> files) {
            if (files.size() != 0){
                for (MultipartFile file : files){
                    uploadFile(request,file);
                }
            }
        return "show";
    }

    public void uploadFile(HttpServletRequest request,MultipartFile file) {
        try {
            // 获取文件所在的路径
            String path = request.getServletContext().getRealPath("/");
            // 设置文件上传之后存放的目录
            File filePath = new File(path + "upload");
            // 判断文件目录是否存在
            if (!filePath.exists()) {
                filePath.mkdir();
            }

            // 文件的名称
            String fileName = file.getOriginalFilename();
            String newName = System.currentTimeMillis() + fileName;

            // 文件转存
            file.transferTo(new File(filePath, newName));
            request.setAttribute("resule", "上传成功");
        } catch (Exception e) {
            request.setAttribute("result", "上传失败");
        }
    }
}