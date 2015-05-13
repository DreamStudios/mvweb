package com.blueshit.neweast.mvpicture.entity;

/**
 * 客户端图片
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 2015/5/13 18:10
 * @description
 */
public class PictureClient {

    private int id;
    private String download_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }
}
