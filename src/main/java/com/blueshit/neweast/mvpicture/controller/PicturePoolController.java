package com.blueshit.neweast.mvpicture.controller;
import com.blueshit.neweast.mvpicture.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PicturePoolController {

	private PictureService pictureService;

    @Autowired
    public PicturePoolController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    /**
     * 客户端获取图片信息
     * @param key 客户端key
     * @return
     */
    @RequestMapping("/getPictures")
    public String getPictrues(String key) throws Exception{
        System.out.println(123);
        return pictureService.getPictures(key);
    }
}
