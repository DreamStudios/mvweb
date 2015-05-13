package com.blueshit.neweast.mvpicture.service.impl;
import com.blueshit.neweast.mvpicture.service.PictureService;
import com.blueshit.neweast.mvpicture.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 2015/1/19 17:26
 * @description
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private PictureService pictureService;

    @Autowired
    public ScheduleServiceImpl(PictureService pictureService) {
        this.pictureService = pictureService;
        syncPicturePool();
    }

    @Scheduled(cron = "0 0/30 * * * ?")   //每30分钟一次
    @Override
    public void syncPicturePool() {
        pictureService.syncPictures();
    }

}
