package de.ollie.ucg.core.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AttributeModelTest {

	private static final String PROPERTY_NAME = "property-name";

	@InjectMocks
	private AttributeModel unitUnderTest;

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
