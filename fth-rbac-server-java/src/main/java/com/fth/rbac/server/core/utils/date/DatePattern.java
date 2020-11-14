package com.fth.rbac.server.core.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author fengtianhe
 * @version $Id: DatePattern.java, v 0.1 2018年10月15日 下午6:36:39 19391 Exp $
 */
public enum DatePattern {

    DEFAULT_PATTERN("yyyyMMddHHmmss", ""),

    YYYY_MM_DD("yyyy-MM-dd", "^\\d{4}-\\d{2}-\\d{2}$"),
    /**
     * 一天的开始
     */
    YYYY_MM_DD_DAY_BEGIN("yyyy-MM-dd 00:00:00", "^\\d{4}-\\d{2}-\\d{2} 00\\:00\\:00$"),
    /**
     * 一天的结束
     */
    YYYY_MM_DD_DAY_END("yyyy-MM-dd 23:59:59", "^\\d{4}-\\d{2}-\\d{2} 23\\:59\\:59$"),

    YYYYMMDD("yyyyMMdd", "^\\d{4}\\d{2}\\d{2}$"),

    YYYYMMDDHHMMSS("yyyy-MM-dd HH:mm:ss", "^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}\\:\\d{2}\\:\\d{2}$"),

    TIMEMILLIONS("yyyyMMddHHmmssSSS", ""),

    YYYYMMDDTHHMMSSSSSZ("yyyy-MM-dd'T'HH:mm:ss.SSS Z", "^\\d{4}-\\d{2}-\\d{2}T\\d{2}\\:\\d{2}\\:\\d{2}\\.\\d{3}Z$"),

    YYYYMMDDTHHMMSS("yyyy-MM-dd'T'HH:mm:ss", "^\\d{4}-\\d{2}-\\d{2}T\\d{2}\\:\\d{2}\\:\\d{2}$"),

    YYYY_MM_DD_CHINESE("yyyy年MM月dd日", "^\\d{4}年\\d{2}月\\d{2}日$"),

    MM_DD_CHINESE("MM月dd日", "^\\d{2}月\\d{2}日$");

    private String pattern;

    private String regularExpression;

    private SimpleDateFormat sdf;

    private DatePattern(String pattern, String regularExpression) {
        this.pattern = pattern;
        this.regularExpression = regularExpression;
        sdf = new SimpleDateFormat(pattern);
    }

    public String getPattern() {
        return pattern;
    }

    public String getRegularExpression() {
        return regularExpression;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    /**
     * 按照一定的时间格式输出字符串日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formate(Date date, DatePattern pattern) {
        return date == null ? "" : pattern.getSdf().format(date);
    }

    /**
     * 按照一定的时间格式转化成日期
     *
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parse(String date, DatePattern pattern) throws ParseException {
        return pattern.getSdf().parse(date);
    }

    /**
     * @param date
     * @return
     */
    public static DatePattern getDatePattern(String date) {
        for (DatePattern pattern : DatePattern.values()) {
            if (Pattern.compile(pattern.getRegularExpression()).matcher(date).matches()) {
                return pattern;
            }
        }
        throw new IllegalArgumentException("未知的时间格式date=" + date);
    }
}
