package com.blueshit.neweast.utils;

/**
 * 常量工具类
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 2015/5/11 14:08
 * @description
 */
public class Constant {
    //每页默认条数
    public static final int PAGE_SIZE = 30;
    //默认密钥
    public static final String DECRYPTKEY          = "52711966";

    //Redis相关键
    public static class RedisKey{
        //图片详情
        public static final String PICTURE = "picture";
        //图片分类(1:学生 2:模特 3:短裙 4:制服 5:蕾丝 6街拍 7:野外 8:空姐 9:日系 10:写真 11:香肩美腿)
        public static final String PTYPE = "ptype:";
    }

    //基本常量
    public static class Common{
        //默认编码
        public static final String URLENC = "UTF-8";
    }
}
