package com.nateshao.mybatis.service;

import com.nateshao.mybatis.mapper.StudentMapper;
import com.nateshao.mybatis.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/11/11 15:43
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

    public List<Student> queryStudent() {
        return studentMapper.queryStudent();
    }

    public Student queryStudentsById(Integer stuNo) {
        return studentMapper.queryStudentsById(stuNo);
    }

    public int insertStudentById(Student student){
        return studentMapper.insertStudentById(student);
    }
}
