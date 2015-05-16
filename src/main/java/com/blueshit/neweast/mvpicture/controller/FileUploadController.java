package com.blueshit.neweast.mvpicture.controller;

import com.blueshit.neweast.utils.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传工具类
 */
@Controller
public class FileUploadController {

    /**
     * 图片上传
     * @param file
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = {"/fileUpload"}, method = {RequestMethod.POST})
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file, HttpSession session, HttpServletRequest request) throws Exception{
        String sessionId = request.getParameter("sessionId");
        ServletContext context = RequestContextUtils.getWebApplicationContext(request).getServletContext();
        String realPath = context.getRealPath("/")+"/upload/";

        if("mvpicture".equals(sessionId)){//mn图片上传
            mvPictureUpload(realPath,file,session);
        }
        return null;
    }

    //mn图片上传
    private void mvPictureUpload(String realPath,MultipartFile file,HttpSession session) throws Exception{
        String name = System.currentTimeMillis() + "_" + (int)(Math.random()*100);
        String childDir = "/mvpicture/";
        //扩展名
        String suffix = file.getOriginalFilename();
        suffix = suffix.substring(suffix.length()-4,suffix.length());

        //创建文件存放位置
        FileUtil.mkdirs(realPath + childDir);
        //文件全路径
        String fullName = realPath + childDir + name + suffix;
        if (!file.isEmpty()) {
            FileUtil.transformToFile(file,fullName);
            String uri = "/upload/" + childDir + name + suffix;
            Object pictures = session.getAttribute("pictures");
            List<String> list;
            if(null == pictures){
                list = new ArrayList<String>();
                list.add(uri);
            }else {
                list = (List<String>)pictures;
                list.add(uri);
            }
            session.setAttribute("pictures",list);
        }
    }
}
