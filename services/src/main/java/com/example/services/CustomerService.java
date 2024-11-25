package com.example.services;

import com.example.common.dto.CustomerDTO;
import com.example.common.exceptions.ApplicationException;
import com.example.specification.criteria.CustomerSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

	Page<CustomerDTO> getCustomers(CustomerSearchCriteria criteria, Pageable pageable);

	CustomerDTO getCustomer(Long id) throws ApplicationException;

	CustomerDTO createCustomer(CustomerDTO customerDTO) throws ApplicationException;

	CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) throws ApplicationException;

	void deleteCustomer(Long id) throws ApplicationException;
}
