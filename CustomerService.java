package com.learning.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.dao.CustomerRepository;
import com.learning.domain.Customer;
import com.learning.vo.CustomerVo;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repo;

	public Customer savecustomer(CustomerVo cust) {
		Customer customer=new Customer();
		customer.setId(cust.id);
		customer.setName(cust.name);
		customer.setMobile(cust.mobile);
		customer.setAddress(cust.address);
		return repo.save(customer);
	}

	public Optional<Customer> getcustomer(long id) {
		return repo.findById( id);
	}

	public List<Customer> getcustomers() {
		
		return repo.findAll();
	}
	
	
}
