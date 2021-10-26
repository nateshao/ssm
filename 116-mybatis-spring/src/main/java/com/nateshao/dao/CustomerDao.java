package com.nateshao.dao;

import com.nateshao.po.Customer;

/**
 * @date Created by 邵桐杰 on 2021/10/26 15:07
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public interface CustomerDao {
    // 通过id查询客户
    public Customer findCustomerById(Integer id);
}
