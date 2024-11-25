package com.example;

import com.example.domain.Customer;
import com.example.domain.codebook.CustomerStatusEnum;

public class TestData {

	public static Customer getCustomer() {

		Customer customer = new Customer();

		customer.setId(1L);
		customer.setFirstName("Test");
		customer.setLastName("Test");
		customer.setStatus(CustomerStatusEnum.ACTIVE.getDBObject());

		return customer;
	}
}
