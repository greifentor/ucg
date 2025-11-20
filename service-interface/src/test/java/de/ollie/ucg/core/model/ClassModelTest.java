package de.ollie.ucg.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClassModelTest {

	private static final String PROPERTY_NAME = "property-name";

	@InjectMocks
	private ClassModel unitUnderTest;

	@Nested
	class findPropertyValue_String {

		@Test
		void throwsAnException_passingANullValue_asPropertyName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.findPropertyValue(null));
		}

		@Test
		void returnsAnEmptyOptional_whenClassHasNoProperties() {
			assertTrue(unitUnderTest.findPropertyValue(PROPERTY_NAME).isEmpty());
		}

		@Test
		void returnsAnEmptyOptional_whenClassHasNoProperty_withMatchingName() {
			// Prepare
			unitUnderTest.setProperties(List.of(new Property(PROPERTY_NAME + 1, ";op")));
			// Run & CHeck
			assertTrue(unitUnderTest.findPropertyValue(PROPERTY_NAME).isEmpty());
		}

		@Test
		void returnsAnOptional_withPropertyValue_whenClassHasAProperty_withMatchingName() {
			// Prepare
			String value = "value";
			unitUnderTest.setProperties(List.of(new Property(PROPERTY_NAME, value)));
			// Run & CHeck
			assertEquals(value, unitUnderTest.findPropertyValue(PROPERTY_NAME).get());
		}
	}
}
