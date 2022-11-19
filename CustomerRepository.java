package com.learning.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import com.learning.domain.Customer;

@EnableRedisRepositories
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
