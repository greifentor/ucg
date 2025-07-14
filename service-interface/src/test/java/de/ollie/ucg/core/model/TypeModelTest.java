package de.ollie.ucg.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TypeModelTest {

	private static final String NAME = "name";
	private static final Object VALUE = 42;

	@InjectMocks
	private TypeModel unitUnderTest;

	@Nested
	class addProperty_String_Object {

		@Test
		void throwsAnException_passingANullValueAsPropertyName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.addProperty(null, VALUE));
		}

		@Test
		void throwsAnException_passingANullValueAsPropertyValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.addProperty(NAME, null));
		}

		@Test
		void returnsItself() {
			assertSame(unitUnderTest, unitUnderTest.addProperty(NAME, VALUE));
		}

		@Test
		void addsThePassedProperty_withCorrectName() {
			// Run
			unitUnderTest.addProperty(NAME, VALUE);
			// Check
			assertEquals(NAME, unitUnderTest.getProperties().get(0).getName());
		}

		@Test
		void addsThePassedProperty_withCorrectValue() {
			// Run
			unitUnderTest.addProperty(NAME, VALUE);
			// Check
			assertEquals(VALUE, unitUnderTest.getProperties().get(0).getValue());
		}
	}
}
