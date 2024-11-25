package com.example.managers.codebook;

import com.example.common.exceptions.ApplicationError;
import com.example.common.exceptions.ApplicationException;
import com.example.domain.AbstractCodeBookEntity;
import com.example.domain.codebook.CustomerStatus;
import com.example.repositories.CustomerStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CustomerStatusManagerImpl implements DefaultCodeBookManager {

	private final CustomerStatusRepository customerStatusRepository;

	@Override
	public AbstractCodeBookEntity getCodeBookEntity(Long id) throws ApplicationException {

		Optional<CustomerStatus> customerStatusOptional = customerStatusRepository.findById(id);

		if (customerStatusOptional.isEmpty()) {
			log.warn("Failed getting customer status: customer status {} doesn't exist!", id);
			throw new ApplicationException(ApplicationError.NON_EXISTING_CUSTOMER_STATUS, String.format("Failed getting customer status: customer status %s doesn't exist!", id));
		}

		return customerStatusOptional.get();
	}
}
