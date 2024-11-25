package com.example.rest;

import com.example.common.dto.CustomerDTO;
import com.example.common.exceptions.ApplicationException;
import com.example.services.CustomerService;
import com.example.specification.criteria.CustomerSearchCriteria;
import com.example.utils.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

	private final CustomerService customerService;

	@GetMapping("")
	public ResponseEntity<ResponseMessage<Page<CustomerDTO>>> getCustomers(CustomerSearchCriteria criteria,
																		   @PageableDefault(sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
		return ResponseEntity.ok(new ResponseMessage<>(customerService.getCustomers(criteria, pageable)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseMessage<CustomerDTO>> getCustomer(@PathVariable Long id) throws ApplicationException {
		return ResponseEntity.ok(new ResponseMessage<>(customerService.getCustomer(id)));
	}

	@PostMapping("")
	public ResponseEntity<ResponseMessage<CustomerDTO>> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) throws ApplicationException {
		return ResponseEntity.ok(new ResponseMessage<>(customerService.createCustomer(customerDTO)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseMessage<CustomerDTO>> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO) throws ApplicationException {
		return ResponseEntity.ok(new ResponseMessage<>(customerService.updateCustomer(id, customerDTO)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseMessage<String>> deleteCustomer(@PathVariable Long id) throws ApplicationException {
		customerService.deleteCustomer(id);
		return ResponseEntity.ok(new ResponseMessage<>("Customer successfully deleted!"));
	}
}
