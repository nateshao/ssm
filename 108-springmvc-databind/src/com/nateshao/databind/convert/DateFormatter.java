package com.nateshao.databind.convert;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @date Created by 邵桐杰 on 2021/10/17 15:24
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description: 使用Formatter自定义日期转换器
 */
public class DateFormatter implements Formatter<Date> {
    // 定义日期格式
    String datePattern = "yyyy-MM-dd HH:mm:ss";
    // 声明SimpleDateFormat对象
    private SimpleDateFormat simpleDateFormat;

    @Override
    public String print(Date date, Locale locale) {
        return new SimpleDateFormat().format(date);
    }

    @Override
    public Date parse(String source, Locale locale) throws ParseException {
        simpleDateFormat = new SimpleDateFormat(datePattern);
        return simpleDateFormat.parse(source);
    }
}
