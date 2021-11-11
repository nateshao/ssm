package com.nateshao.mybatis.mapper;

import com.nateshao.mybatis.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Date;
import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/11/11 15:58
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@SpringBootTest
public class StudentMapperTest {
    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void queryStudent() {
        List<Student> students = studentMapper.queryStudent();
        for (Student student : students) {
            System.out.println(student);
        }
    }

    @Test
    public void addStudent() {
        Student student = new Student();
        student.setStuNo(1128);
        student.setBirthday(new Date());
        student.setEntranceTime(new Date());
        student.setMajor("千羽");
        student.setNation("汉");
        student.setPolitical("aaaa");
        student.setSchool("广东");
        student.setStudName("千羽");
        student.setSex("男");
        int i = studentMapper.insertStudentById(student);
        System.out.println("成功插入" + i + "条记录");
    }


}