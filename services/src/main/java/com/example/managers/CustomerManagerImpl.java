package com.example.managers;

import com.example.common.exceptions.ApplicationError;
import com.example.common.exceptions.ApplicationException;
import com.example.domain.Customer;
import com.example.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerManagerImpl implements CustomerManager {

	private final CustomerRepository customerRepository;

	@Override
	public Customer getCustomerById(Long id) throws ApplicationException {

		Optional<Customer> customerOptional = customerRepository.findCustomerById(id);

		if (customerOptional.isEmpty()) {
			log.warn("Failed getting customer: customer with id {} doesn't exist!", id);
			throw new ApplicationException(ApplicationError.NON_EXISTING_CUSTOMER, String.format("Failed getting customer: customer with id %s doesn't exist!", id));
		}

		return customerOptional.get();
	}
}
