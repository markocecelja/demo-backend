package com.example.common.dto.codebook;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeBookEntryDTO {

	private String id;

	@Size(max = 128)
	private String name;
}
