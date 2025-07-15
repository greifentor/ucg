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
class GeneratorSettingTest {

	private static final String PROPERTY_NAME = "property-name";
	private static final String PROPERTY_VALUE = "property-value";

	@InjectMocks
	private GeneratorSetting unitUnderTest;

	@Nested
	class getPropertyByName_String {

		@Test
		void throwsAnException_passingANullValue_asPropertyName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getPropertyByName(null));
		}

		@Test
		void returnsAnEmptyOptional_passingAnUnknownPropertyName() {
			// Prepare
			unitUnderTest.setProperties(List.of());
			// Run & Check
			assertTrue(unitUnderTest.getPropertyByName(PROPERTY_NAME).isEmpty());
		}

		@Test
		void returnsAnOptional_withTheCorrectValue_passingAKnownPropertyName() {
			// Prepare
			unitUnderTest.setProperties(List.of(new Property(PROPERTY_NAME, PROPERTY_VALUE)));
			// Run & Check
			assertEquals(PROPERTY_VALUE, unitUnderTest.getPropertyByName(PROPERTY_NAME).get());
		}
	}
}
