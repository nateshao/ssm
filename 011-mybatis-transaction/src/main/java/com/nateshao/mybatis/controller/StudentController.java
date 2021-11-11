package com.nateshao.mybatis.controller;

import com.nateshao.mybatis.model.Student;
import com.nateshao.mybatis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/11/11 15:41
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

    @RequestMapping("/queryStudents")
    public @ResponseBody
    List<Student> queryStudent() {
        List<Student> students = studentService.queryStudent();
//        for (Student student : students) {
//            System.out.println(student);
//        }
        return students;
    }

    @RequestMapping("/queryStudentsById")
    public @ResponseBody
    Student queryStudentsById() {
        Student student = studentService.queryStudentsById(1);
        return student;
    }

    @RequestMapping("/insertStudentById")
    public @ResponseBody void insertStudentById() {
        Student student = new Student();
        student.setStuNo(1126);
        student.setBirthday(new Date());
        student.setEntranceTime(new Date());
        student.setMajor("千羽");
        student.setNation("汉");
        student.setPolitical("aaaa");
        student.setSchool("广东");
        student.setStudName("千羽");
        student.setSex("男");
        studentService.insertStudentById(student);

    }
}
