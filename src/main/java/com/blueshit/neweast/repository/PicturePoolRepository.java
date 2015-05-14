package com.blueshit.neweast.repository;

import java.util.List;

/**
 * 图片池数据库操作接口
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 2015/5/11 13:38
 * @description
 */
public interface PicturePoolRepository {


    //初始化图片信息
    public void initPicturePool();

    /**
     * 获取图片池列表
     * @param ptype 图片分类
     * @return
     */
    public List<String> getPicturePool(String ptype);

    /**
     * 通过ID获取Redis中的广告信息
     * @param id
     * @return
     */
    public String getPictureById(String id);
}
