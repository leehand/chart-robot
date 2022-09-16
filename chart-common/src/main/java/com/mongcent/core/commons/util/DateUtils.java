package com.mongcent.core.commons.util;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

/**
 * @Description 日期，一律以东7时区
 * @Author zl
 * @Date 2019/10/21
 **/
public class DateUtils {

    private static ZoneId zoneId = ZoneId.of("Asia/Phnom_Penh");

    /**
     * 今天
     *
     * @return 字符串，以"yyyy-MM-dd"的格式返回
     * 比如：2019-10-21
     */
    public static String today() {
        LocalDate localDate = LocalDate.now(zoneId);
        return localDate.toString();
    }

    /**
     * 昨天
     *
     * @return 字符串，以"yyyy-MM-dd"的格式返回
     * 比如：2019-10-20
     */
    public static String yesterday() {
        LocalDate localDate = LocalDate.now(zoneId);
        return localDate.minusDays(1).toString();
    }

    /**
     * 明天
     *
     * @return 字符串，以"yyyy-MM-dd"的格式返回
     * 比如：2019-10-22
     */
    public static String tomorrow() {
        LocalDate localDate = LocalDate.now(zoneId);
        return localDate.plusDays(1).toString();
    }

    /**
     * 这个星期一
     * 星期一为开始，周日是最后一日
     *
     * @return
     */
    public static String monday() {
        LocalDate localDate = LocalDate.now(zoneId);
        return localDate.with(DayOfWeek.MONDAY).toString();
    }

    /**
     * 上个星期一
     * 星期一为开始，周日是最后一日
     *
     * @return
     */
    public static String lastMonday() {
        LocalDate localDate = LocalDate.now(zoneId);
        return localDate.minusDays(7).with(DayOfWeek.MONDAY).toString();
    }

    /**
     * n周后的周一
     *
     * @param week
     * @return
     */
    public static String addMonday(int week) {
        LocalDate localDate = LocalDate.now(zoneId);
        return localDate.plusDays(7 * week).with(DayOfWeek.MONDAY).toString();
    }

    /**
     * 这个月1号
     *
     * @return
     */
    public static String month1() {
        LocalDate localDate = LocalDate.now(zoneId);
        return localDate.withDayOfMonth(1).toString();
    }

    public static String lastMonth1() {
        LocalDate localDate = LocalDate.now(zoneId);
        return localDate.minusMonths(1).withDayOfMonth(1).toString();
    }

    /**
     * n月后的1号
     *
     * @param month
     * @return
     */
    public static String addMonth1(int month) {
        LocalDate localDate = LocalDate.now(zoneId);
        return localDate.plusMonths(month).withDayOfMonth(1).toString();
    }

    public static String addMonth1(String date, int month) {
        LocalDate localDate = LocalDate.parse(date);
        return localDate.plusMonths(month).withDayOfMonth(1).toString();
    }

    /**
     * 本月最后一天
     *
     * @return
     */
    public static String monthLastDay() {
        LocalDate localDate = LocalDate.now(zoneId);
        //本月的最后一天
        return localDate.with(TemporalAdjusters.lastDayOfMonth()).toString();
    }

    /**
     * 指定月的最后一天
     *
     * @param date
     * @return
     */
    public static String monthLastDay(String date) {
        LocalDate localDate = LocalDate.parse(date);
        //本月的最后一天
        return localDate.with(TemporalAdjusters.lastDayOfMonth()).toString();
    }

    /**
     * N天后。可以为负，负就是N天前
     *
     * @param n
     * @return
     */
    public static String addDay(int n) {
        LocalDate localDate = LocalDate.now(zoneId);
        return localDate.plusDays(n).toString();
    }

    /**
     * 自定义日期加减
     *
     * @param date
     * @param n
     * @return
     */
    public static String addDay(String date, int n) {
        LocalDate localDate = LocalDate.parse(date);
        return localDate.plusDays(n).toString();
    }

    public static void main(String[] args) {
        System.out.println(YearMonth.now());
    }
}
