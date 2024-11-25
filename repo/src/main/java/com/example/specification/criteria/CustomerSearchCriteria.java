package com.example.specification.criteria;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerSearchCriteria {

	private String name;

	private Long status;

}
