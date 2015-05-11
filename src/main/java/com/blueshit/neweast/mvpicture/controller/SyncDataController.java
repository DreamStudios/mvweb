package com.blueshit.neweast.mvpicture.controller;
import com.blueshit.neweast.mvpicture.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据同步相关
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 2015/4/22 16:22
 * @description
 */
@RestController
public class SyncDataController {
	
	private PictureService pictureService;

    @Autowired
    public SyncDataController(PictureService pictureService) {
        this.pictureService = pictureService;
    }
	

	/**
	 * 图片信息同步
	 * @return
	 */
	@RequestMapping(value = "/admin/syncPictures")
	public String syncSuspendPool(){
		return "0";
	}
}
