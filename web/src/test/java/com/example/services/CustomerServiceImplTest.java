package com.example.services;

import com.example.ApplicationWebContextAwareUT;
import com.example.TestData;
import com.example.common.dto.CustomerDTO;
import com.example.common.dto.codebook.CodeBookEntryDTO;
import com.example.common.exceptions.ApplicationError;
import com.example.common.exceptions.ApplicationException;
import com.example.common.mappers.CustomerMapper;
import com.example.domain.Customer;
import com.example.domain.codebook.CustomerStatusEnum;
import com.example.managers.CustomerManagerImpl;
import com.example.managers.codebook.CustomerStatusManagerImpl;
import com.example.repositories.CustomerRepository;
import com.example.specification.CustomerSearchSpecification;
import com.example.specification.criteria.CustomerSearchCriteria;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest extends ApplicationWebContextAwareUT {

	@InjectMocks
	private CustomerServiceImpl customerService;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private CustomerManagerImpl customerManager;

	@Mock
	private CustomerStatusManagerImpl customerStatusManager;

	@Mock
	private CustomerMapper customerMapper;

	@Mock
	private Specification<Customer> customerSearchSpecification;

	@Test
	void getCustomers() {

		CustomerSearchCriteria customerSearchCriteria = CustomerSearchCriteria.builder()
				.name("test")
				.status(CustomerStatusEnum.ACTIVE.getId())
				.build();

		when(customerRepository.findAll(eq(customerSearchSpecification), any(Pageable.class))).thenReturn(Page.empty());

		ArgumentCaptor<CustomerSearchCriteria> argument = ArgumentCaptor.forClass(CustomerSearchCriteria.class);

		try (MockedStatic<CustomerSearchSpecification> specification = mockStatic(CustomerSearchSpecification.class)) {
			specification.when(() -> CustomerSearchSpecification.findCustomers(any())).thenReturn(customerSearchSpecification);

			assertDoesNotThrow(() -> customerService.getCustomers(customerSearchCriteria, Pageable.unpaged()));

			specification.verify(() -> CustomerSearchSpecification.findCustomers(argument.capture()));
			verify(customerMapper, times(0)).customerToCustomerDTO(any(Customer.class));
		}

		CustomerSearchCriteria capturedCriteria = argument.getValue();

		assertNotNull(capturedCriteria);
		assertEquals(customerSearchCriteria.getName(), capturedCriteria.getName());
		assertEquals(customerSearchCriteria.getStatus(), capturedCriteria.getStatus());
	}

	@Test
	void getCustomer() throws ApplicationException {

		Customer customer = TestData.getCustomer();

		when(customerManager.getCustomerById(any())).thenReturn(customer);

		ArgumentCaptor<Customer> argument = ArgumentCaptor.forClass(Customer.class);

		assertDoesNotThrow(() -> customerService.getCustomer(any()));
		verify(customerMapper, times(1)).customerToCustomerDTO(argument.capture());

		Customer capturedCustomer = argument.getValue();

		assertNotNull(capturedCustomer);
		assertEquals(customer.getId(), capturedCustomer.getId());
		assertEquals(customer.getFirstName(), capturedCustomer.getFirstName());
		assertEquals(customer.getLastName(), capturedCustomer.getLastName());
		assertNotNull(capturedCustomer.getStatus());
		assertEquals(customer.getStatus().getId(), capturedCustomer.getStatus().getId());
	}

	@Test
	void createCustomer() throws ApplicationException {

		CustomerDTO customerDTO = CustomerDTO.builder()
				.firstName("John")
				.lastName("Doe")
				.build();

		when(customerStatusManager.getCodeBookEntity(any())).thenReturn(CustomerStatusEnum.ACTIVE.getDBObject());

		ArgumentCaptor<Customer> argument = ArgumentCaptor.forClass(Customer.class);

		assertDoesNotThrow(() -> customerService.createCustomer(customerDTO));
		verify(customerRepository, times(1)).save(argument.capture());

		Customer capturedCustomer = argument.getValue();

		verify(customerMapper, times(1)).customerToCustomerDTO(eq(capturedCustomer));

		assertNotNull(capturedCustomer);
		assertEquals(customerDTO.getFirstName(), capturedCustomer.getFirstName());
		assertEquals(customerDTO.getLastName(), capturedCustomer.getLastName());
		assertNotNull(capturedCustomer.getStatus());
		assertEquals(CustomerStatusEnum.ACTIVE.getId(), capturedCustomer.getStatus().getId());
	}

	@Test
	void updateCustomer() throws ApplicationException {

		CustomerDTO customerDTO = CustomerDTO.builder()
				.firstName("John")
				.lastName("Doe")
				.status(CodeBookEntryDTO.builder().id(CustomerStatusEnum.INACTIVE.getId().toString()).build())
				.build();

		when(customerManager.getCustomerById(any())).thenReturn(TestData.getCustomer());
		when(customerStatusManager.getCodeBookEntity(any())).thenReturn(CustomerStatusEnum.INACTIVE.getDBObject());

		ArgumentCaptor<Customer> argument = ArgumentCaptor.forClass(Customer.class);

		assertDoesNotThrow(() -> customerService.updateCustomer(any(), customerDTO));
		verify(customerRepository, times(1)).save(argument.capture());

		Customer capturedCustomer = argument.getValue();

		verify(customerMapper, times(1)).customerToCustomerDTO(eq(capturedCustomer));

		assertNotNull(capturedCustomer);
		assertEquals(customerDTO.getFirstName(), capturedCustomer.getFirstName());
		assertEquals(customerDTO.getLastName(), capturedCustomer.getLastName());
		assertNotNull(capturedCustomer.getStatus());
		assertEquals(CustomerStatusEnum.INACTIVE.getId(), capturedCustomer.getStatus().getId());
	}

	@Test
	void updateCustomer_BadRequest_MissingCustomerStatus() throws ApplicationException {

		CustomerDTO customerDTO = CustomerDTO.builder()
				.firstName("John")
				.lastName("Doe")
				.build();

		when(customerManager.getCustomerById(any())).thenReturn(Mockito.mock(Customer.class));

		assertException(ApplicationError.BAD_REQUEST, () -> customerService.updateCustomer(any(), customerDTO));
	}

	@Test
	void deleteCustomer() throws ApplicationException {

		when(customerManager.getCustomerById(any())).thenReturn(Mockito.mock(Customer.class));

		assertDoesNotThrow(() -> customerService.deleteCustomer(any()));
		verify(customerRepository, times(1)).delete(any(Customer.class));
	}
}
