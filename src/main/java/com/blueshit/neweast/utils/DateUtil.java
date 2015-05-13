package com.blueshit.neweast.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 2015/5/12 17:36
 * @description
 */
public class DateUtil {

    public static String FORMAT_SHORT = "yyyy-MM-dd";
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 获取当前日期前几天或后几天的日期
     * @param day
     * @return
     */
    public static String getPreAfterDate(int day){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);    //负数为前几天  正数后几天
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat(FORMAT_SHORT);//设置默认前一天
        return df.format(date);
    }

    /**
     * 获取当前时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 功能描述：返回月
     * @param date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回日
     * @param date 日期
     * @return 返回日
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小时
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     * @param date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫秒
     * @param date 日期
     * @return 返回毫
     */
    public static long getMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    public static void main(String[] args) {
        System.out.println(getHour(new Date()));
    }
}
