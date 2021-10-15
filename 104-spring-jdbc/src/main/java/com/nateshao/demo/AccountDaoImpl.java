package com.nateshao.demo;

import com.nateshao.jdbc.Account;
import com.nateshao.jdbc.JdbcTemplateTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/10/15 19:32
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class AccountDaoImpl implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public int addAccount(Account account) {
        // 定义SQL
        String sql = "insert into account(username,balance) value(?,?)";
        // 定义数组来存放SQL语句中的参数
        int num = jdbcTemplate.update(sql);
        // 执行添加操作，返回的是受SQL语句影响的记录条数
        return num;
    }

    @Override
    public int updateAccount(int id) {
        String sql = "update account set username = ?,balance = ? where id =? ";
        int num = jdbcTemplate.update(sql);
        return num;
    }

    @Override
    public int deleteAccount(int id) {
        String sql = "delete account  where id =? ";
        int num = jdbcTemplate.update(sql);
        return num;

    }

    @Override
    public List<Account> findById() {
        String sql = "select * from account where id =? ";
        BeanPropertyRowMapper<Account> mapper = new BeanPropertyRowMapper<>();

        List<Account> query = jdbcTemplate.query(sql, mapper);
        for (Account account : query) {
            System.out.println(account);
        }
        return query;
    }

    @Override
    public List<Account> findByAll() {
        String sql = "select * from account ";
        BeanPropertyRowMapper<Account> mapper = new BeanPropertyRowMapper<>();

        List<Account> query = jdbcTemplate.query(sql, mapper);
        for (Account account : query) {
            System.out.println(account);
        }
        return query;

    }
}
