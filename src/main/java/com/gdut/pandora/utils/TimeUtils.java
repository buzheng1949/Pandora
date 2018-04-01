package com.gdut.pandora.utils;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by buzheng on 18/4/1.
 * 时间工具类 没错 我想早点搞定这个项目
 */
@Slf4j
public class TimeUtils {

    public final static long ONE_DAY_SECONDS = 24 * 60 * 60;

    /**
     * 时间格式化、格式
     */
    public static final String DateTimeFormatter = "yyyy-MM-dd HH:mm:ss";

    private static final org.joda.time.format.DateTimeFormatter hourMinuteFormatter = DateTimeFormat.forPattern("HH:mm");

    /**
     * 时间格式化、格式
     */
    public static final String DateTime = "yyyy-MM-dd";

    /**
     * 一小时有多少秒
     */
    public static final int ONE_HOUR_SECONDS = (int) TimeUnit.HOURS.toSeconds(1L);

    /**
     * 把日期格式化成只带年月日的形式
     */
    public static Date dateToYMDDay(Date date) {
        String dateString = getDateToString(date, DateTime);
        return parse2Date(dateString, DateTime);
    }

    /**
     * 把日期类型转化为整形的时间戳
     */
    public static int dateToPeriod(Date date) {
        if (null == date) {
            return -1;
        }
        return (int) (date.getTime() / 1000);
    }

    /**
     * 获取当前整形时间戳
     */
    public static long getCurrentTime() {
        return  (System.currentTimeMillis() / 1000);
    }

    /**
     * 按照格式获取时间
     */
    public static String getDateToString(String formart) {
        return new SimpleDateFormat(formart).format(new Date());
    }

    /**
     * 获取指定时间的前一天的时间
     **/
    public static Date getBeforDay(Date date) {
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(date);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        return calendar.getTime();
    }

    /**
     * 按照格式获取时间
     */
    public static String getDateToString(Date date, String formart) {
        return new SimpleDateFormat(formart).format(date);
    }

    /**
     * 将字符串转换成日期
     */
    public static Date parse2Date(String text, String formatter) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formatter);
            return sdf.parse(text);
        } catch (ParseException e) {
            log.error("string to date is error! text=" + text, e);
            return null;
        }
    }

    /**
     * 获得当天0点时间戳
     */
    public static Long getTimeMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis() / 1000;
    }

    /**
     * 获取后天0点的时间戳
     */
    public static Long getDayAfterTomorrowTime() {
        Long today = getTimeMorning();
        return addDays(today, 2);
    }

    /**
     * 获取当天10点时间戳
     */
    public static int getCurrentDayTen() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    /**
     * 获取0点时间戳
     */
    public static int getDayZero(Long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    /**
     * 取得指定时间后的日期
     */
    public static long addDays(long date, long days) {
        return date + days * ONE_DAY_SECONDS;
    }

    /**
     * 当前小时 24小时制
     */
    public static int getCurrentHour() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取指定时间的小时 24小时制
     */
    public static int getHour(long date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date * 1000);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取startTime对应的 日期描述 “昨日/今日/明日/xx日”
     *
     * @param startTime   开始时间
     * @param currentTime 当前时间
     * @return
     */
    public static String getDateDesc(int startTime, int currentTime) {
        int zeroSTOfToday = toZeroTimeSecondsOfToday(currentTime);//今天零点
        int zeroSTOfYesterday = zeroSTOfToday - (int) ONE_DAY_SECONDS;//昨天零点
        int zeroSTOfTomorrow = zeroSTOfToday + (int) ONE_DAY_SECONDS;//明天零点
        int zeroSTOfAfterTomorrow = zeroSTOfTomorrow + (int) ONE_DAY_SECONDS;//后天零点

        String dateDesc = "今日";
        if (startTime >= zeroSTOfYesterday && startTime < zeroSTOfToday) {
            dateDesc = "昨日";
        } else if (startTime >= zeroSTOfTomorrow && startTime < zeroSTOfAfterTomorrow) {
            dateDesc = "明日";
        } else if (startTime < zeroSTOfYesterday || startTime >= zeroSTOfAfterTomorrow) {
            dateDesc = displayTimeStamp(startTime, "dd日");
        }

        return dateDesc;
    }

    public static String getHourMinuteDesc(int startTime) {
        return hourMinuteFormatter.print(TimeUnit.SECONDS.toMillis(startTime));
    }

    /**
     * 将时间戳格式化
     *
     * @param dateTime  时间戳
     * @param formatter 时间格式
     * @return 时间字符串
     */
    public static String displayTimeStamp(long dateTime, String formatter) {
        if (dateTime == 0) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(formatter);
        return format.format(new Date(dateTime * 1000L));
    }

    /**
     * 转换“时间”到“那天的零点时刻”。
     *
     * @param timeSeconds 时间
     * @return 那天的零点时刻
     */
    public static int toZeroTimeSecondsOfToday(int timeSeconds) {
        final Calendar calendar = Calendar.getInstance();
        long timeMillis = TimeUnit.SECONDS.toMillis(timeSeconds);
        calendar.setTimeInMillis(timeMillis);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return (int) (TimeUnit.MILLISECONDS.toSeconds(calendar.getTimeInMillis()));
    }

}
