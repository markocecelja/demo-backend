package com.example.managers;

import com.example.ApplicationWebContextAwareUT;
import com.example.common.exceptions.ApplicationError;
import com.example.domain.Customer;
import com.example.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CustomerManagerImplTest extends ApplicationWebContextAwareUT {

	@InjectMocks
	private CustomerManagerImpl customerManager;

	@Mock
	private CustomerRepository customerRepository;

	@Test
	void getCodebookEntity() {
		when(customerRepository.findCustomerById(any())).thenReturn(Optional.of(Mockito.mock(Customer.class)));

		assertDoesNotThrow(() -> customerManager.getCustomerById(any()));
	}

	@Test
	void getCodebookEntity_NonExistingFileTemplateType() {
		when(customerRepository.findCustomerById(any())).thenReturn(Optional.empty());

		assertException(ApplicationError.NON_EXISTING_CUSTOMER, () -> customerManager.getCustomerById(any()));
	}
}
