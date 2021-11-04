package com.nateshao.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;

/**
 * @date Created by 邵桐杰 on 2021/10/22 23:20
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory = null;

    // 初始化SqlSessionFactory对象
    static {
        try {
            // 使用MyBatis提供的Resources类加载MyBatis的配置文件
            Reader reader =
                    Resources.getResourceAsReader("mybatis-config.xml");
            // 构建SqlSessionFactory工厂
            sqlSessionFactory =
                    new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取SqlSession对象的静态方法
    public static SqlSession getSession() {
        return sqlSessionFactory.openSession();
    }
}
