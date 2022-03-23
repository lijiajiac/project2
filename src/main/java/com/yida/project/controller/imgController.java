package com.yida.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class imgController {
    //配置上传文件的目录
    public String uploadFolder = "public/";
    //配置文件的访问url
    public String baseUrl = "http://localhost:8080/" + uploadFolder;


    @ResponseBody
    @RequestMapping(value = "/getAjaxUploadFiles", method = RequestMethod.POST)
    public String imger(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) throws IOException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                System.out.println(file.getName());
                if (!file.isEmpty()) {
                    String name = file.getName();// 获取文件上传组建的名称即：<input type="File" name="yida"> name的属性yida
                    String Filename = file.getOriginalFilename();// 获取上传文件的名称
                    String type = file.getContentType();// 获取文件的类型
                    // 限定文件的类型为jpg，png格式。若不符合抛出异常
                    if (type.endsWith("jpg") || type.endsWith("png") || type.endsWith("jpeg")) {
                        String size = getFileSize(file.getSize());// 获取文件大小 kb M
                        System.out.println(size);
                        //图片url
                        String url = baseUrl + Filename;
                        System.out.println("图片url：" + url);

                        String realPath = request.getServletContext().getRealPath("/");
                        String ss = getFileName2(Filename);
                        File f = new File(realPath + ss, Filename);
                        if (!f.exists()) {
                            f.mkdirs();
                        }
                        resultMap.put(ss + '&' + Filename, size);
                        file.transferTo(f);
                    }
                }
            }
            return mapToJson(resultMap);
        }
        resultMap.put("code", 401);//封装回调信息
        resultMap.put("msg", "上传失败");
        return mapToJson(resultMap);

    }

    @ResponseBody
    @RequestMapping(value = "/getAjaxUploadFiles3", method = RequestMethod.POST)
    public String imger3(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                System.out.println(file.getName());
                if (!file.isEmpty()) {
                    String Filename = file.getOriginalFilename();// 获取上传文件的名称
                    String type = file.getContentType();// 获取文件的类型
                    // 限定文件的类型为jpg，png格式。若不符合抛出异常
                    if (type.endsWith("jpg") || type.endsWith("png") || type.endsWith("jpeg")) {
                        String size = getFileSize(file.getSize());// 获取文件大小 kb M
                        String realPath = request.getServletContext().getRealPath("/");
                        File f = new File(realPath + getFileName2(Filename), Filename);
                        if (!f.exists()) {
                            f.mkdirs();
                        }
                        resultMap.put(getFileName2(Filename) + '&' + Filename, size);
                        file.transferTo(f);
                    }
                }
            }
           /* resultMap.put("code", 200);
            resultMap.put("msg", "上传成功");*/
            return mapToJson(resultMap);
        }
        resultMap.put("code", 401);//封装回调信息
        resultMap.put("msg", "上传失败");
        return mapToJson(resultMap);

    }

    @ResponseBody
    @RequestMapping(value = "/getAjaxUploadFiles2", method = RequestMethod.POST)
    public String imger2(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                if (!file.isEmpty()) {
                    String Filename = file.getOriginalFilename();// 获取上传文件的名称
                    String type = file.getContentType();// 获取文件的类型
                    // 限定文件的类型为jpg，png格式。若不符合抛出异常
                    if (type.endsWith("mp4")) {
                        String size = getFileSize(file.getSize());// 获取文件大小 kb M
                        //文件上传目录
                        String realPath = request.getServletContext().getRealPath("/");
                        String ss = getFileName2(Filename);
                        String folder = "mp4/";
                        File uploadDir = new File(realPath + folder + ss, Filename);
                        if (!uploadDir.exists()) { //没有文件夹则创建
                            uploadDir.mkdirs();
                        }
                        String sizes = size.substring(0, size.length() - 2);
                        //文件上传
                        file.transferTo(uploadDir);
                        resultMap.put(folder + ss + "&" + Filename, sizes + "MB");
                    }
                }
            }
            return mapToJson(resultMap);
        }
        resultMap.put("code", 401);//封装回调信息
        resultMap.put("msg", "上传失败");
        return mapToJson(resultMap);

    }

    //把map对象转成json字符串
    public String mapToJson(Map<String, Object> map) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String strJson = mapper.writeValueAsString(map);
            return strJson;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // 计算文件大小
    public String getFileSize(long size) {
        // 1G=1024M, 1M=1024K, 1K=1024B
        String strSize = null;
        if (size >= 1024 * 1024 * 1024) {// G
            // %表示语法，必须写，.3表示小数点后保留3位有效数字，f表示格式化浮点数，G只是随便写的。
            strSize = String.format("%.3fG", size / (1024 * 1024 * 1024.0));
        } else if (size >= 1024 * 1024) {// M
            strSize = String.format("%.3fM", size / (1024 * 1024.0));
        } else if (size >= 1024) {// K
            strSize = String.format("%.3fK", size / 1024.0);
        } else {// B
            strSize = size + "B";
        }
        return strSize;
    }

    public String getFileName2(String name) {
        int code = name.hashCode();// 获取hashCode码
        int first = code & 0XF;// 第一层目录
        int second = code & (0XF >> 1);// 第二层目录
        String str1 = UUID.randomUUID().toString();// 随机字符串
        //String str2 = name.substring(name.indexOf("."));// 文件后辍
        //String str = first + "/" + second + "/" + str1 + str2;
        String str = first + "/" + second + "/" + str1;
        return str;
    }

}
