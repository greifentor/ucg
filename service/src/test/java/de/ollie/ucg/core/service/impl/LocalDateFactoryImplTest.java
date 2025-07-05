package de.ollie.ucg.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LocalDateFactoryImplTest {

	@InjectMocks
	private LocalDateFactoryImpl unitUnderTest;

	@Nested
	class now {

		@Test
		void returnsALocalDate() {
			assertNotNull(unitUnderTest.now());
		}

		@Test
		void returnsANewLocalDateOnEachCall() {
			assertNotSame(unitUnderTest.now(), unitUnderTest.now());
		}
	}
}
