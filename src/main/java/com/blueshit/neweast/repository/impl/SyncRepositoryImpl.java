package com.blueshit.neweast.repository.impl;

import java.util.List;
import java.util.Map;

import com.blueshit.neweast.repository.SyncRepository;
import com.blueshit.neweast.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 图片信息同步至Redis实现类
 * @author zhaohuaan
 *
 */
@Repository
public class SyncRepositoryImpl implements SyncRepository{

    private ObjectMapper objectMapper;
	private StringRedisTemplate stringRedisTemplate;
    @Autowired
    public SyncRepositoryImpl(ObjectMapper objectMapper, StringRedisTemplate stringRedisTemplate) {
        this.objectMapper = objectMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    //同步商品信息详情
    @Override
    public void syncPictureDetail(Map<String, String> map) throws Exception {
        stringRedisTemplate.boundHashOps(Constant.RedisKey.PICTURE).putAll(map);
    }

    //同步图片分类池
    @Override
    public void syncPicturePool(Map<Integer,List<Integer>> map) throws Exception {
        for(Integer key : map.keySet()){
            String json = objectMapper.writeValueAsString(map.get(key));
            stringRedisTemplate.boundValueOps(Constant.RedisKey.PTYPE + key).set(json);
        }
    }
}
