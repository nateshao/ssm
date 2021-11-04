package com.nateshao.mybatis.controller;

import com.nateshao.mybatis.pojo.Student;
import com.nateshao.mybatis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @date Created by 邵桐杰 on 2021/11/4 16:24
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;


    @RequestMapping(value = "/student")
    public @ResponseBody Student student() {
        Student student = studentService.queryStudent(1);
        return student;
    }


}
