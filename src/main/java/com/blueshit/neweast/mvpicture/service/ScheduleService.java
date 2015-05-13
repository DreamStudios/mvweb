package com.blueshit.neweast.mvpicture.service;

/**
 * 定时任务执行
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 2015/1/19 17:26
 * @description
 */
public interface ScheduleService {

    //自动同步图片池
    public void syncPicturePool();
}
