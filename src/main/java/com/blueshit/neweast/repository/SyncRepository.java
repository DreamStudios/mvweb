package com.blueshit.neweast.repository;
import java.util.List;
import java.util.Map;

/**
 * 图片信息同步至Redis接口
 * @author zhaohuaan
 *
 */
public interface SyncRepository {

    //同步图片详情信息
    public void syncPictureDetail(Map<String, String> map) throws Exception;

    //同步图片分类池
    public void syncPicturePool(Map<Integer,List<Integer>> map) throws Exception;
}
