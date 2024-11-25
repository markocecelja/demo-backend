package com.example.common.mappers;

import com.example.common.dto.codebook.CodeBookEntryDTO;
import com.example.domain.AbstractCodeBookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CodeBookMapper {

	public abstract CodeBookEntryDTO codeBookEntityToCodeBookEntryDTO(AbstractCodeBookEntity entity);
}
