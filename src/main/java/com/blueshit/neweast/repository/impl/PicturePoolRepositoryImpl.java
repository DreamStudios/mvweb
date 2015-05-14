package com.blueshit.neweast.repository.impl;

import com.blueshit.neweast.repository.PicturePoolRepository;
import com.blueshit.neweast.utils.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 图片池数据库操作实现类
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 2015/5/11 13:38
 * @description
 */
@Repository
public class PicturePoolRepositoryImpl implements PicturePoolRepository{
    private static final Logger logger = LoggerFactory.getLogger(PicturePoolRepositoryImpl.class);

    //结构：键为ptype 值为ptype下的所有值。
    private Map<String, List<String>> picturePools;

    private StringRedisTemplate stringRedisTemplate;
    private ObjectMapper        objectMapper;

    @Autowired
    public PicturePoolRepositoryImpl(StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void initPicturePool() {
        try {
            picturePools = initPoolsByKey(Constant.RedisKey.PTYPE);
        } catch (Exception ex) {
            logger.error("读取redis中数据出错",ex);
        }
    }

    /**
     * 根据键(通配符)加载redis数据
     *
     * @param keyForPool
     * @return
     * @throws Exception
     */
    private Map<String,List<String>> initPoolsByKey(String keyForPool) throws Exception {
        Map<String, List<String>> map = new HashMap<String,List<String>>();

        Iterator<String> iterator = stringRedisTemplate.keys(keyForPool + "*").iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            initPoolByKey(map, key);
        }
        return map;

    }

    /**
     * 根据键加载Redis数据
     * @param map
     * @param keyForPoolNum
     * @throws Exception
     */
    private void initPoolByKey(Map<String, List<String>> map, String keyForPoolNum) throws Exception {
        Integer[] pids = objectMapper.readValue(stringRedisTemplate.boundValueOps(keyForPoolNum).get(), Integer[].class);
        List<String> list = new ArrayList<String>();
        for(Integer pid : pids){
            list.add(String.valueOf(pid));
        }
        map.put(keyForPoolNum.split(":")[1],list);
    }

    /**
     * 获取图片池列表
     * @param ptype 图片分类
     * @return
     */
    @Override
    public List<String> getPicturePool(String ptype){
        if(null == picturePools){
            initPicturePool();
        }
        return picturePools.get(ptype);
    }

    /**
     * 通过ID获取Redis中的广告信息
     * @param id
     * @return
     */
    @Override
    public String getPictureById(String id){
        return stringRedisTemplate.boundHashOps(Constant.RedisKey.PICTURE).get(id).toString();
    }
}
