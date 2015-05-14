package com.blueshit.neweast.mvpicture.service;

import com.blueshit.neweast.mvpicture.entity.Picture;
import org.springframework.data.domain.Page;

/**
 * 图片管理接口
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 2015/5/11 13:37
 * @description
 */
public interface PictureService {

    /**
     * 分页查询图片信息
     * @param pageNo 当前页
     * @param style 图片形式(1:自有，2:第三方)
     * @param ptype 图片分类
     * @param status 运行状态(1开启，0暂停)
     * @return
     */
    public Page<Picture> getPicturePage(int pageNo,int style,int ptype,int status);

    /**
     * 修改图片投放状态
     * @param ids 图片ID列表
     * @param status 投放状态
     * @return
     */
    public boolean updatePictureStatus(String ids,int status);

    /**
     * 修改图片权重信息
     * @param id 广告ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param weight 权重
     * @param hour 投放时间段
     * @return
     */
    public boolean updatePictureWeight(int id,String startTime,String endTime,int weight,String hour);

    /**
     * 根据ID获取图片实体信息
     * @param id 图片ID
     * @return
     */
    public Picture getPictureById(int id);

    /**
     * 添加图片
     * @param picture 图片对象
     * @return
     */
    public boolean addPicture(Picture picture);

    /**
     * 同步图片信息
     * @return
     */
    public boolean syncPictures();

    /**
     * 客户端请求图片信息
     * @param key
     * @return
     */
    public String getPictures(String key) throws Exception;
}
