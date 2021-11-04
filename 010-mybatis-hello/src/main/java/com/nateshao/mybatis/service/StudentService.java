package com.nateshao.mybatis.service;

import com.nateshao.mybatis.mapper.StudentMapper;
import com.nateshao.mybatis.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date Created by 邵桐杰 on 2021/11/4 16:23
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public Student queryStudent(Integer id) {
        return studentMapper.queryStudentById(id);
    }
}

