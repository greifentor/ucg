package de.ollie.ucg.template.processor.velocity.adapter.wrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NameSeparatorTest {

	@Nested
	class getNameSeparated_String {

		@Test
		void throwsAnException_passingANullValue_asName() {
			assertThrows(IllegalArgumentException.class, () -> NameSeparator.INSTANCE.getNameSeparated(null, "_"));
		}

		@Test
		void throwsAnException_passingANullValue_asSeparator() {
			assertThrows(IllegalArgumentException.class, () -> NameSeparator.INSTANCE.getNameSeparated("Bla", null));
		}

		@Test
		void returnsAnUnchangedNameString_whenNameIsEmpty() {
			// Prepare
			String expected = "";
			// Run & Check
			assertEquals(expected, NameSeparator.INSTANCE.getNameSeparated("", "_"));
		}

		@Test
		void returnsAnUnchangedNameString_whenNameContainsLowerCaseLetterOnly() {
			// Prepare
			String expected = "lowercaseonly";
			// Run & Check
			assertEquals(expected, NameSeparator.INSTANCE.getNameSeparated("lowercaseonly", "_"));
		}

		@Test
		void returnsAnUnchangedNameString_whenNameContainsLowerCaseLetterOnly_exceptFirstLetter() {
			// Prepare
			String expected = "Leadinguppercaseonly";
			// Run & Check
			assertEquals(expected, NameSeparator.INSTANCE.getNameSeparated("Leadinguppercaseonly", "_"));
		}

		@Test
		void returnsAnNameString_withASeparator_beforeEachUpperCaseLetter_exceptFirstLetter() {
			// Prepare
			String expected = "A_Camel_Case_Name";
			// Run & Check
			assertEquals(expected, NameSeparator.INSTANCE.getNameSeparated("ACamelCaseName", "_"));
		}
	}
}
