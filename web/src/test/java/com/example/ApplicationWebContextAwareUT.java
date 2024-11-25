package com.example;

import com.example.common.exceptions.ApplicationError;
import com.example.common.exceptions.ApplicationException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public abstract class ApplicationWebContextAwareUT {

	public static void assertException(ApplicationError error, Executable executable) {
		ApplicationException exception = assertThrows(ApplicationException.class, executable);
		assertEquals(error, exception.getError());
	}
}
