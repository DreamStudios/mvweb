package com.blueshit.neweast.mvpicture.controller;
import com.blueshit.neweast.mvpicture.entity.Picture;
import com.blueshit.neweast.mvpicture.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 图片管理
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 2015/4/22 16:22
 * @description
 */
@Controller
public class PictureController {

    private PictureService pictureService;

    @Autowired
    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    //进入图片管理页面
    @RequestMapping("/admin/pictureManager")
    public String pictureManager(Model model, Integer newPage,Integer style,Integer ptype,Integer status) {
        newPage = newPage == null ? 1 : newPage;
        style = style == null ? 0 : style;
        ptype = ptype == null ? 0 : ptype;
        status = status == null ? -1 : status;
        model.addAttribute("pages", pictureService.getPicturePage(newPage,style,ptype,status));
        model.addAttribute("newPage", newPage);
        model.addAttribute("style", style);
        model.addAttribute("ptype", ptype);
        model.addAttribute("status", status);
        return "admin/pictureManager";
    }

    //修改图片投放状态
    @RequestMapping("/admin/updateStatus")
    public String updateStatus(RedirectAttributes attr,String ids, Integer status){
        boolean result = pictureService.updatePictureStatus(ids, status);
        if(result){
            attr.addFlashAttribute("res","状态修改成功");
        }else {
            attr.addFlashAttribute("res","状态修改失败");
        }
        return "redirect:pictureManager";
    }

    //修改图片投放权值
    @RequestMapping("/admin/updateWeight")
    public String updateStatus(RedirectAttributes attr,int id,String startTime,String endTime, int weight,String hour){
        boolean result = pictureService.updatePictureWeight(id,startTime,endTime,weight,hour);
        if(result){
            attr.addFlashAttribute("res","修改成功");
        }else {
            attr.addFlashAttribute("res","修改失败");
        }
        return "redirect:pictureManager";
    }

    //进入图片添加页面
    @RequestMapping(value="/admin/pictureAdd", method = RequestMethod.GET)
    public String pictureAdd(Model model, Integer style, HttpSession session){
        session.removeAttribute("pictures");
        Picture picture = new Picture();
        style = style == null ? 1 : style;
        picture.setStyle(style);
        model.addAttribute("picture",picture);
        System.out.println("------------------");
        return "admin/pictureAdd";
    }

    //添加图片
    @RequestMapping(value="/admin/pictureAdd", method = RequestMethod.POST)
    public String addGoodsStatusPost(@ModelAttribute("picture") Picture picture,BindingResult result, HttpSession session,RedirectAttributes attr){
        boolean res = pictureService.addPicture(picture);
        session.removeAttribute("pictures");
        if(res){
            attr.addFlashAttribute("res","添加成功");
        }else {
            attr.addFlashAttribute("res","添加失败");
        }
        return "redirect:pictureAdd";
    }


    //进入图片查看页面
    @RequestMapping(value="/admin/pictureView", method = RequestMethod.GET)
    public String pictureEdit(Model model, int id){
        Picture picture = pictureService.getPictureById(id);
        model.addAttribute("picture",picture);
        return "admin/pictureView";
    }
}
