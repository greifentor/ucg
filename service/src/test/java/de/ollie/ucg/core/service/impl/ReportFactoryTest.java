package de.ollie.ucg.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportFactoryTest {

	@InjectMocks
	private ReportFactory unitUnderTest;

	@Nested
	class create {

		@Test
		void returnsANewObject() {
			assertNotNull(unitUnderTest.create());
		}

		@Test
		void returnsANewObject_onEachCall() {
			assertNotSame(unitUnderTest.create(), unitUnderTest.create());
		}
	}
}
