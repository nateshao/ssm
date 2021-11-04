package com.nateshao.mybatis;

import com.nateshao.mybatis.mapper.StudentMapper;
import com.nateshao.mybatis.pojo.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyBatisApplicationTests {
    @Autowired
    private StudentMapper studentMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void queryStudentById(){
        Student student = studentMapper.queryStudentById(1);
        System.out.println(student);
    }

}
