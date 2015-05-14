package com.blueshit.neweast.mvpicture.bean;

/**
 * 客户端请求实体
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 2015/5/14 8:50
 * @description
 */
public class PictureRequest {
    //图片分类
    private String ptype;
    //页码
    private int page;

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
