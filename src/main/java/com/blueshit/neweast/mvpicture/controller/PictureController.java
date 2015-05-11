package com.blueshit.neweast.mvpicture.controller;
import com.blueshit.neweast.mvpicture.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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



    /*@RequestMapping(value="/admin/pictureAddOrEdit", method = RequestMethod.GET)
    public String addGoodsStatusGet(Model model, Integer id, HttpSession session){
        session.removeAttribute("goodsIcon");
        Goods goods = null;
        if(goodsid == null){ //添加
            goods = new Goods();
            goods.setGoodsIcon(Constant.DEFAULT_PICTURE);
            goods.setWeight(10);
        }else{ //修改
            goods = goodsService.getGoodsByGoodsId(goodsid);
            session.setAttribute("goodsIcon",goods.getGoodsIcon()==null?"":goods.getGoodsIcon());
        }
        model.addAttribute("goods",goods);
        return "admin/pictureAddOrEdit";
    }*/

//    /**
//     * 添加/修改 商品信息 - post
//     * @param model 跳转携带信息
//     * @param goods 商品对象
//     * @return
//     */
//    @RequestMapping(value="/admin/goods_manage_add", method = RequestMethod.POST)
//    public String addGoodsStatusPost(Model model,@ModelAttribute("goods") Goods goods, HttpSession session){
//        try {
//            if(goods.getGoodsType()!=1){
//                goods.setGoodsStyle(0);
//            }
//            String goodsIcon = (String) session.getAttribute("goodsIcon");
//            if (StringUtils.isEmpty(goodsIcon)){
//                model.addAttribute("goods",goods);
//                model.addAttribute("add_icon","请上传图片");
//                return "admin/goods_manage_add";
//            }
//            goods.setGoodsIcon(goodsIcon);
//            goodsService.addGoods(goods);
//            session.removeAttribute("goodsIcon");
//        } catch (Exception e) {
//            logger.error((goods.getGoodsId()==0?"添加":"修改")+"商品信息出错", e);
//            model.addAttribute("goods",goods);
//            return "admin/goods_manage_add";
//        }
//        return "redirect:goods_manage";
//    }

}
