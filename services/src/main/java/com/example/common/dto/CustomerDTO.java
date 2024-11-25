package com.example.common.dto;

import com.example.common.dto.codebook.CodeBookEntryDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {

	private String id;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	private CodeBookEntryDTO status;
}
