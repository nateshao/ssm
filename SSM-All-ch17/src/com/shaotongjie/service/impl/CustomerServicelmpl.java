package com.shaotongjie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shaotongjie.dao.CustomerDao;
import com.shaotongjie.po.Customer;
import com.shaotongjie.service.CustomerService;

@Service
@Transactional
public class CustomerServicelmpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public Customer findCustomerById(Integer id) {
        // TODO Auto-generated method stub
        return this.customerDao.findCustomerByld(id);
    }

}
