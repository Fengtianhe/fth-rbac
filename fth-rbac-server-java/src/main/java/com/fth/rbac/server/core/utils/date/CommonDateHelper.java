package com.fth.rbac.server.core.utils.date;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * @author fengtianhe
 * @version $Id: CommonDateUtil.java, v 0.1 2018年10月15日 下午6:36:34 19391 Exp $
 */
public class CommonDateHelper {

    static String DEFAULT_FORMATER = "%02d";

    /**
     * 两个日期之间的天数
     * <p>
     * date [2017-07-03, 2017-07-03], return 0
     * date [2017-07-03, 2017-07-04], return 1
     * date [2017-07-04, 2017-07-03], return -1
     * date ["2017-07-03T01:01:01", 2017-07-03], return 0
     *
     * @return
     */
    public static int dateDiff(Date date1, Date date2) {
        return new Period(new DateTime(date1), new DateTime(date2), PeriodType.days()).getDays();
    }

    public static Long getMillis(String time) {
        return new DateTime(time).getMillis();
    }

    public static Date parse(String date) {
        return parse(date, DatePattern.YYYY_MM_DD);
    }

    public static Date parse(String date, DatePattern pattern) {
        try {
            return DatePattern.parse(date, pattern);
        } catch (ParseException e) {
            throw new IllegalArgumentException("时间转换错误");
        }
    }

    /**
     * 获取天数较小的那天
     *
     * @param expectRepaymentDate
     * @param repaymentDate
     * @return
     */
    public static Date minByDate(Date expectRepaymentDate, Date repaymentDate) {
        return dateDiff(expectRepaymentDate, repaymentDate) > 0 ? expectRepaymentDate
                : repaymentDate;
    }

    /**
     * 获取天数较大的那天
     *
     * @param expectRepaymentDate
     * @param repaymentDate
     * @return
     */
    public static Date maxByDate(Date expectRepaymentDate, Date repaymentDate) {
        return dateDiff(expectRepaymentDate, repaymentDate) > 0 ? repaymentDate
                : expectRepaymentDate;
    }

    /**
     * 判断两个日期是否相等(是否为同一天)
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean dateEquals(Date date1, Date date2) {
        return dateDiff(date1, date2) == 0 ? true : false;
    }

    /**
     * 日期不相等
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean dateNotEquals(Date date1, Date date2) {
        return !dateEquals(date1, date2);
    }

    public static boolean lt(Date date1, Date date2) {
        return dateDiff(date1, date2) > 0 ? true : false;
    }

    public static boolean gt(Date date1, Date date2) {
        return dateDiff(date1, date2) < 0 ? true : false;
    }

    public static String format() {
        return format(new Date());
    }

    public static String format(Date date) {
        return format(date, DatePattern.YYYY_MM_DD);
    }

    public static String format(DatePattern pattern) {
        return format(new Date(), pattern);
    }

    public static String format(Date date, DatePattern pattern) {
        return DatePattern.formate(date, pattern);
    }

    public static Date[] getBillPeriod(int payDay) {
        Date[] billPeriod = new Date[2];
        LocalDate now = LocalDate.now();
        if (now.getDayOfMonth() <= payDay) {
            billPeriod[0] = localDateToUdate(now.minusMonths(2).withDayOfMonth(payDay - 1));
            billPeriod[1] = localDateToUdate(now.minusMonths(1).withDayOfMonth(payDay));
        } else {
            billPeriod[0] = localDateToUdate(now.minusMonths(1).withDayOfMonth(payDay - 1));
            billPeriod[1] = localDateToUdate(now.withDayOfMonth(payDay));
        }
        return billPeriod;
    }

    public static Date localDateToUdate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zoneId).toInstant();
        return Date.from(instant);
    }

    public static Date minusDays(Integer days) {
        return localDateToUdate(LocalDate.now().minusDays(days));
    }

    public static Date plusMonth(int monthsToAdd) {
        return plusMonth(new Date(), monthsToAdd);
    }

    public static Date plusMonth(Date date, int monthsToAdd) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDateToUdate(localDate.plusMonths(monthsToAdd));
    }

    public static Date plusDays(Integer days) {
        return plusDays(new Date(), days);
    }

    public static Date plusDays(Date date, Integer daysToAdd) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDateToUdate(localDate.plusDays(daysToAdd));
    }

    public static int getYear() {
        return LocalDate.now().getYear();
    }

    public static int getMonth() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.MONTH) + 1;
    }

    public static int getDay() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.DATE);
    }

    public static Date getStartOfDay(Date date) {
        return setTime(date, 0, 0, 0, 0);
    }

    public static Date getEndOfDay(Date date) {
        return setTime(date, 23, 59, 59, 999);
    }

    public static Date plusHours(Integer hoursToAdd) {
        return plusHours(new Date(), hoursToAdd);
    }

    public static Date plusHours(Date date, Integer hoursToAdd) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.HOUR_OF_DAY, hoursToAdd);
        return ca.getTime();
    }

    public static Date setTime(Date date, int hour, int minute, int second, int millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);
        return calendar.getTime();
    }

    /**
     * 获取指定日期的下一天
     *
     * @param date
     * @return
     */
    public static Date getNextDate(Date date) {
        return getNextDate(date, 1);
    }

    /**
     * 获取指定日期n天后的日期
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getNextDate(Date date, int n) {
        return new Date(date.toInstant().plus(Duration.ofDays(n)).toEpochMilli());
    }

    /**
     * 获取指定日期前一天
     *
     * @param date
     * @return
     */
    public static Date getPreDate(Date date) {
        return getPreDate(date, 1);
    }

    /**
     * 获取指定日期n天前的日期
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getPreDate(Date date, int n) {
        return new Date(date.toInstant().minus(Duration.ofDays(n)).toEpochMilli());
    }

    /**
     * 获取指定日期年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        return now.get(Calendar.YEAR);
    }

    /**
     * 获取指定日期月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        return now.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取指定日期日
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        return now.get(Calendar.DATE);
    }

    /**
     * 获取当月具体某一天
     *
     * @param days
     * @return
     */
    public static Date getDate(Integer days) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }

    /**
     * 获取当天的当月第一天
     *
     * @return
     */
    public static Date getMonthFirstDay() {
        return getMonthFirstDay(new Date(), 0);
    }

    /**
     * 获取指定日期的前n个月第一天
     *
     * @param date
     * @return
     */
    public static Date getMonthFirstDay(Date date, int monthNum) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.MONTH, 0 - monthNum);
        now.set(Calendar.DAY_OF_MONTH, 1);
        return now.getTime();
    }

    /**
     * 获取当天的当月最后一天
     *
     * @return
     */
    public static Date getMonthLastDay() {
        return getMonthLastDay(new Date(), 0);
    }

    /**
     * 获取指定日期的前n个月最后一天
     *
     * @param date
     * @return
     */
    public static Date getMonthLastDay(Date date, int monthNum) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.MONTH, 0 - monthNum);
        now.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH));
        return now.getTime();
    }

    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static boolean isWeekend(Date date) {
        int dayOfWeek = getDayOfWeek(date);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY ? true : false;
    }

    public static Date getPreWorkingDay(Date date) {
        while (isWeekend(date)) {
            date = getPreDate(date);
        }
        return date;
    }

    public static int getUnix10(Date date) {
        return (int) (date.getTime() / 1000);
    }

    public static Date unixToDate(int time) {
        return unixToDate(time * 1000l);
    }

    public static Date unixToDate(long time) {
        Date date = new Date();
        date.setTime(time);
        return date;
    }

    public static Date getPreMinute(Date date, int minute) {
        long t = date.getTime() - minute * 60 * 1000L;
        return unixToDate(t);
    }

    public static String getYearMonth(int years, int months) {
        return years + "-" + String.format(DEFAULT_FORMATER, months);
    }

    public static String getYearMonthDay(int years, int months, int day) {
        return getYearMonth(years, months) + "-" + String.format(DEFAULT_FORMATER, day);
    }

    public static BigDecimal secondToHour(int second) {
        return new BigDecimal(second).divide(new BigDecimal(3600), 2, BigDecimal.ROUND_HALF_UP);
    }

    public static int getCurrentAge(Date birthday) {
        //年龄 = 当前年 - 出生年
        int age = getYear() - getYear(birthday);
        if (age <= 0) {
            return 0;
        }
        // 如果当前月份小于出生月份: age-1
        // 如果当前月份等于出生月份, 且当前日小于出生日: age-1
        if ((getMonth() < getMonth(birthday)) || (getMonth() == getMonth(birthday) && getDay() <= getDay(birthday))) {
            age--;
        }
        return age < 0 ? 0 : age;
    }

    public static Calendar getCalendar(String datetime, DatePattern pattern) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(datetime, pattern));
        return cal;
    }

    /**
     * 获取小时
     *
     * @param datetime 时间
     */
    public static int getHour(String datetime, DatePattern pattern) {
        return getCalendar(datetime, pattern).get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取分钟
     *
     * @param datetime 时间
     */
    public static int getMinute(String datetime, DatePattern pattern) {
        return getCalendar(datetime, pattern).get(Calendar.MINUTE);
    }
}
