package com.nateshao.mybatis.mapper;

import com.nateshao.mybatis.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @date Created by 邵桐杰 on 2021/11/4 21:35
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Mapper
public interface StudentMapper {

    public Student queryStudentById(Integer id);
}
