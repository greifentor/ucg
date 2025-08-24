package de.ollie.ucg.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TypeModelTest {

	private static final String NAME = "name";
	private static final String PROPERTY_NAME = "property-name";
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
			assertEquals(VALUE.toString(), unitUnderTest.getProperties().get(0).getValue());
		}
	}

	@Nested
	class hasProperty_String {

		@Test
		void throwsAnException_passingANullValue_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.hasProperty(null));
		}

		@Test
		void returnsFalse_whenAttributeHasNoProperties() {
			// Prepare
			unitUnderTest.setProperties(List.of());
			// Run & Check
			assertFalse(unitUnderTest.hasProperty(PROPERTY_NAME));
		}

		@Test
		void returnsFalse_whenAttributeHasNoMatchingProperty() {
			// Prepare
			unitUnderTest.setProperties(List.of(new Property().setName(PROPERTY_NAME + 1)));
			// Run & Check
			assertFalse(unitUnderTest.hasProperty(PROPERTY_NAME));
		}

		@Test
		void returnsTrue_whenAttributeHasAMatchingProperty() {
			// Prepare
			unitUnderTest.setProperties(List.of(new Property().setName(PROPERTY_NAME)));
			// Run & Check
			assertTrue(unitUnderTest.hasProperty(PROPERTY_NAME));
		}
	}
}
