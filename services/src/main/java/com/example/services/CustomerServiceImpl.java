package com.example.services;

import com.example.common.dto.CustomerDTO;
import com.example.common.exceptions.ApplicationError;
import com.example.common.exceptions.ApplicationException;
import com.example.common.mappers.CustomerMapper;
import com.example.domain.Customer;
import com.example.domain.codebook.CustomerStatus;
import com.example.domain.codebook.CustomerStatusEnum;
import com.example.managers.CustomerManager;
import com.example.managers.codebook.DefaultCodeBookManager;
import com.example.repositories.CustomerRepository;
import com.example.specification.CustomerSearchSpecification;
import com.example.specification.criteria.CustomerSearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;

	private final CustomerManager customerManager;

	@Qualifier("customerStatusManagerImpl")
	private final DefaultCodeBookManager customerStatusManager;

	private final CustomerMapper customerMapper;

	@Override
	public Page<CustomerDTO> getCustomers(CustomerSearchCriteria criteria, Pageable pageable) {

		Page<Customer> customers = customerRepository.findAll(CustomerSearchSpecification.findCustomers(criteria), pageable);

		return customers.map(customerMapper::customerToCustomerDTO);
	}

	@Override
	public CustomerDTO getCustomer(Long id) throws ApplicationException {

		Customer customer = customerManager.getCustomerById(id);

		return customerMapper.customerToCustomerDTO(customer);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public CustomerDTO createCustomer(CustomerDTO customerDTO) throws ApplicationException {

		Customer customer = new Customer();
		customer.setFirstName(customerDTO.getFirstName().trim());
		customer.setLastName(customerDTO.getLastName().trim());

		CustomerStatus customerStatus = (CustomerStatus) customerStatusManager.getCodeBookEntity(CustomerStatusEnum.ACTIVE.getId());
		customer.setStatus(customerStatus);

		customerRepository.save(customer);

		return customerMapper.customerToCustomerDTO(customer);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) throws ApplicationException {

		Customer customer = customerManager.getCustomerById(id);

		customer.setFirstName(customerDTO.getFirstName().trim());
		customer.setLastName(customerDTO.getLastName().trim());

		if (customerDTO.getStatus() == null || !StringUtils.hasLength(customerDTO.getStatus().getId())) {
			log.warn("Update customer failed: customer status is required!");
			throw new ApplicationException(ApplicationError.BAD_REQUEST, "Update customer failed: customer status is required!");
		}

		Long statusId = Long.valueOf(customerDTO.getStatus().getId());

		if (!customer.getStatus().getId().equals(statusId)) {
			CustomerStatus customerStatus = (CustomerStatus) customerStatusManager.getCodeBookEntity(statusId);
			customer.setStatus(customerStatus);
		}

		customerRepository.save(customer);

		return customerMapper.customerToCustomerDTO(customer);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteCustomer(Long id) throws ApplicationException {

		Customer customer = customerManager.getCustomerById(id);

		customerRepository.delete(customer);
	}
}
