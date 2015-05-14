package com.blueshit.neweast.mvpicture.bean;

import java.util.ArrayList;
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

    private List<ImageData> data = new ArrayList<ImageData>();

    public List<ImageData> getData() {
        return data;
    }

    public void setData(List<ImageData> data) {
        this.data = data;
    }
}
