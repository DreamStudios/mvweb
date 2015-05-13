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

    //结构：外层map,键为ptype 值为ptype下的所有值。
    //内层map,键为页码,值为该页的所有图片ID
    private Map<String, Map<Integer, List<String>>> picturePools;

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
            logger.error("读取redis中数据出错");
        }
    }

    /**
     * 根据键(通配符)加载redis数据
     *
     * @param keyForPool
     * @return
     * @throws Exception
     */
    private Map<String, Map<Integer, List<String>>> initPoolsByKey(String keyForPool) throws Exception {
        Map<String, Map<Integer, List<String>>> map = new HashMap<String, Map<Integer, List<String>>>();

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
    private void initPoolByKey(Map<String, Map<Integer, List<String>>> map, String keyForPoolNum) throws Exception {
        Map<Integer, List<String>> pageMap = new HashMap<Integer, List<String>>();
        Integer[] adids = objectMapper.readValue(stringRedisTemplate.boundValueOps(keyForPoolNum).get(), Integer[].class);
        //TODO 将分页后的信息加入map
        map.put(keyForPoolNum.split(":")[1],pageMap);
    }

    /**
     * 获取图片池列表
     * @param ptype 图片分类
     * @param page 页码
     * @return
     */
    @Override
    public List<String> getPicturePool(String ptype,int page){
        if(null == picturePools){
            initPicturePool();
        }
        return picturePools.get(ptype).get(page);
    }
}
