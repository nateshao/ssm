package com.nateshao.databind.convert;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @date Created by 邵桐杰 on 2021/10/17 15:25
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description: 自定义日期转换器
 */
public class DateConverter implements Converter<String, Date> {
    // 定义日期格式
    private String datePattern = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Date convert(String source) {
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        try {
            return sdf.parse(source);
        } catch (ParseException e) {
            throw new IllegalArgumentException(
                    "无效的日期格式，请使用这种格式:" + datePattern);
        }
    }
}
