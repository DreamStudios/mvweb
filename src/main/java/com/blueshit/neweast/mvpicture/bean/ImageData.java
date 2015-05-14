package com.blueshit.neweast.mvpicture.bean;

/**
 * 发送给客户端的图片信息实体类
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 2015/4/22 16:22
 * @description
 */
public class ImageData {
    //主键
    private int id;
    //地址
	public String download_url;

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
