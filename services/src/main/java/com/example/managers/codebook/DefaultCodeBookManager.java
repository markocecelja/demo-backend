package com.example.managers.codebook;

import com.example.common.exceptions.ApplicationException;
import com.example.domain.AbstractCodeBookEntity;

public interface DefaultCodeBookManager {

	AbstractCodeBookEntity getCodeBookEntity(Long id) throws ApplicationException;
}
