package com.learning.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learning.domain.Customer;
import com.learning.service.CustomerService;
import com.learning.vo.CustomerVo;

@RestController
public class LearningController {

	@Autowired
	CustomerService service;
	@GetMapping("/hi")
	public String gethello() {
		return "Wellcome ";
	}
	
	@PostMapping("/save")
	public Customer save(@RequestBody CustomerVo cust) {
		Customer savecustomer = service.savecustomer(cust);
		return savecustomer;
		
	}
	@GetMapping("/{id}")
	public Optional<Customer> getcustomer(@PathVariable long id) {
		return  service.getcustomer(id);
	}
	
	@GetMapping("/all")
	public List<Customer> getcustomers() {
		return  service.getcustomers();
	}
}
