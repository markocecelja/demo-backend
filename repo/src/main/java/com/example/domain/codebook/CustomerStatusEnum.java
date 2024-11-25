package com.example.domain.codebook;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomerStatusEnum {

	ACTIVE(1L),
	INACTIVE(2L);

	private final Long id;

	public CustomerStatus getDBObject() {
		CustomerStatus customerStatus = new CustomerStatus();
		customerStatus.setId(this.getId());
		customerStatus.setName(this.name());
		return customerStatus;
	}
}
