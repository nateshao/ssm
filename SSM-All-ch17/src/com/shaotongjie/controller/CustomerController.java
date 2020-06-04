package com.shaotongjie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shaotongjie.po.Customer;
import com.shaotongjie.service.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/findCustomerById")
	public String findCustomerById(Integer id,Model model) {
		Customer customer = customerService.findCustomerById(id);
		model.addAttribute("customer",customer);
		return "customer";
	}
}
