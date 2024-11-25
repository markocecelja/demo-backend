package com.example.managers.codebook;

import com.example.ApplicationWebContextAwareUT;
import com.example.common.exceptions.ApplicationError;
import com.example.domain.codebook.CustomerStatus;
import com.example.repositories.CustomerStatusRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CustomerStatusManagerImplTest extends ApplicationWebContextAwareUT {

	@InjectMocks
	private CustomerStatusManagerImpl customerStatusManager;

	@Mock
	private CustomerStatusRepository customerStatusRepository;

	@Test
	void getCodebookEntity() {
		when(customerStatusRepository.findById(any())).thenReturn(Optional.of(Mockito.mock(CustomerStatus.class)));

		assertDoesNotThrow(() -> customerStatusManager.getCodeBookEntity(any()));
	}

	@Test
	void getCodebookEntity_NonExistingFileTemplateType() {
		when(customerStatusRepository.findById(any())).thenReturn(Optional.empty());

		assertException(ApplicationError.NON_EXISTING_CUSTOMER_STATUS, () -> customerStatusManager.getCodeBookEntity(any()));
	}
}
