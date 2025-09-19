package de.ollie.ucg.core.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PropertyOwnerTest {

	private static final String NAME = "name";
	private static final String VALUE = "value";

	@Mock
	private Property anotherProperty;

	@Mock
	private Property property;

	@Spy
	private PropertyOwner unitUnderTest;

	@Nested
	class asPropertyWithValue_String_String {

		@Test
		void throwsAnException_passingANullValue_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.hasPropertyWithValue(null, null));
		}

		@Test
		void returnsFalse_whenPropertyOwner_hasNullProperties() {
			// Prepare
			when(unitUnderTest.getProperties()).thenReturn(null);
			// Run & Check
			assertFalse(unitUnderTest.hasPropertyWithValue(NAME, VALUE));
		}

		@Test
		void returnsFalse_whenPropertyOwner_hasNoProperties() {
			// Prepare
			when(unitUnderTest.getProperties()).thenReturn(List.of());
			// Run & Check
			assertFalse(unitUnderTest.hasPropertyWithValue(NAME, VALUE));
		}

		@Test
		void returnsFalse_passingAnUnKnownPropertyName() {
			// Prepare
			when(property.getName()).thenReturn(NAME + 1);
			when(unitUnderTest.getProperties()).thenReturn(List.of(property));
			// Run & Check
			assertFalse(unitUnderTest.hasPropertyWithValue(NAME, VALUE));
		}

		@Test
		void returnsFalse_passingAKnownPropertyName_butValueDoesNotMatch() {
			// Prepare
			when(property.getName()).thenReturn(NAME);
			when(property.getValue()).thenReturn(VALUE + 1);
			when(unitUnderTest.getProperties()).thenReturn(List.of(property));
			// Run & Check
			assertFalse(unitUnderTest.hasPropertyWithValue(NAME, VALUE));
		}

		@Test
		void returnsFalse_passingAKnownPropertyName_butPassedValueIsNull() {
			// Prepare
			when(property.getName()).thenReturn(NAME);
			when(unitUnderTest.getProperties()).thenReturn(List.of(property));
			// Run & Check
			assertFalse(unitUnderTest.hasPropertyWithValue(NAME, null));
		}

		@Test
		void returnsTrue_passingAKnownPropertyName_andMatchingValue() {
			// Prepare
			when(property.getName()).thenReturn(NAME);
			when(property.getValue()).thenReturn(VALUE);
			when(unitUnderTest.getProperties()).thenReturn(List.of(property));
			// Run & Check
			assertTrue(unitUnderTest.hasPropertyWithValue(NAME, VALUE));
		}

		@Test
		void returnsTrue_passingAKnownPropertyName_andMatchingValue_withOtherUnmatchingOneCheckedBefore() {
			// Prepare
			when(anotherProperty.getName()).thenReturn(NAME);
			when(anotherProperty.getValue()).thenReturn(VALUE + 1);
			when(property.getName()).thenReturn(NAME);
			when(property.getValue()).thenReturn(VALUE);
			when(unitUnderTest.getProperties()).thenReturn(List.of(anotherProperty, property));
			// Run & Check
			assertTrue(unitUnderTest.hasPropertyWithValue(NAME, VALUE));
		}
	}

	@Nested
	class hasPropertyWithName_String {

		@Test
		void throwsAnException_passingANullValue_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.hasPropertyWithName(null));
		}

		@Test
		void returnsFalse_whenOwner_hasNoProperties_nullList() {
			// Prepare
			when(unitUnderTest.getProperties()).thenReturn(null);
			// Run & Check
			assertFalse(unitUnderTest.hasPropertyWithName(NAME));
		}

		@Test
		void returnsFalse_whenOwner_hasNoProperties_emptyList() {
			// Prepare
			when(unitUnderTest.getProperties()).thenReturn(List.of());
			// Run & Check
			assertFalse(unitUnderTest.hasPropertyWithName(NAME));
		}

		@Test
		void returnsFalse_whenOwner_hasNoProperties_withMatchingName() {
			// Prepare
			when(property.getName()).thenReturn(NAME + 1);
			when(unitUnderTest.getProperties()).thenReturn(List.of(property));
			// Run & Check
			assertFalse(unitUnderTest.hasPropertyWithName(NAME));
		}

		@Test
		void returnsTrue_whenOwner_hasAtLeastOneProperties_withMatchingName() {
			// Prepare
			when(property.getName()).thenReturn(NAME);
			when(unitUnderTest.getProperties()).thenReturn(List.of(property));
			// Run & Check
			assertTrue(unitUnderTest.hasPropertyWithName(NAME));
		}
	}
}
