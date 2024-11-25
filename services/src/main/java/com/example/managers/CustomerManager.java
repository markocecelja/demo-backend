package com.example.managers;

import com.example.common.exceptions.ApplicationException;
import com.example.domain.Customer;

public interface CustomerManager {

	Customer getCustomerById(Long id) throws ApplicationException;
}
