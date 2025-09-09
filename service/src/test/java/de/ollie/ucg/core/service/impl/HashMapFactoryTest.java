package de.ollie.ucg.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HashMapFactoryTest {

	@InjectMocks
	private HashMapFactory unitUnderTest;

	@Nested
	class createMapStringObject {

		@Test
		void returnsAnObject() {
			assertNotNull(unitUnderTest.createMapStringObject());
		}

		@Test
		void returnsANewObjectOnEachCall() {
			assertNotSame(unitUnderTest.createMapStringObject(), unitUnderTest.createMapStringObject());
		}
	}
}
