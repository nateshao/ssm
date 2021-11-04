package com.shaotongjie.dao;

import com.shaotongjie.po.Customer;

public interface CustomerDao {
    /* *根据id查询客户信息*/
    public Customer findCustomerByld(Integer id);
}
