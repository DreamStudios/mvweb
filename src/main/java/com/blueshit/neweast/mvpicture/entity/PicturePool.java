package com.blueshit.neweast.mvpicture.entity;

import java.util.List;

/**
 * 下发给客户端的信息
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 2015/5/13 18:00
 * @description
 */
public class PicturePool {

    private List<PictureClient> data;

    public List<PictureClient> getData() {
        return data;
    }

    public void setData(List<PictureClient> data) {
        this.data = data;
    }
}
