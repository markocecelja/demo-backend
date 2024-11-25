package com.example.common.mappers;

import com.example.common.dto.CustomerDTO;
import com.example.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CodeBookMapper.class})
public abstract class CustomerMapper {

	@Mappings({
			@Mapping(target = "status", source = "status")
	})
	public abstract CustomerDTO customerToCustomerDTO(Customer entity);
}
