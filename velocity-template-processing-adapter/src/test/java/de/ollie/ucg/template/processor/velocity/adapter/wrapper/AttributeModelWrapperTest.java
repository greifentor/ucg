package de.ollie.ucg.template.processor.velocity.adapter.wrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.ucg.core.model.AttributeModel;
import de.ollie.ucg.core.model.Model;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AttributeModelWrapperTest {

	private static final String ATTRIBUTE_NAME = "attribute-name";
	private static final String DEFAULT_VALUE = "default-value";

	@Mock
	private AttributeModel attributeModel;

	@Mock
	private Model model;

	@InjectMocks
	private AttributeModelWrapper unitUnderTest;

	@Nested
	class getDefaultValue {

		@Test
		void returnsAttributesDefautlValue() {
			// Prepare
			when(attributeModel.getDefaultValue()).thenReturn(DEFAULT_VALUE);
			// Run & Check
			assertEquals(DEFAULT_VALUE, unitUnderTest.getDefaultValue());
		}
	}

	@Nested
	class getName {

		@Test
		void returnsAttributeName() {
			// Prepare
			when(attributeModel.getName()).thenReturn(ATTRIBUTE_NAME);
			// Run & Check
			assertEquals(ATTRIBUTE_NAME, unitUnderTest.getName());
		}
	}

	@Nested
	class getNameSeparated_String {

		@Test
		void throwsAnException_passingANullValue_asSeparator() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getNameSeparated(null));
		}

		@Test
		void returnsAnUnchangedNameString_whenNameContainsLowerCaseLetterOnly() {
			// Prepare
			String expected = "lowercaseonly";
			when(attributeModel.getName()).thenReturn(expected);
			// Run & Check
			assertEquals(expected, unitUnderTest.getNameSeparated("_"));
		}

		@Test
		void returnsAnUnchangedNameString_whenNameContainsLowerCaseLetterOnly_exceptFirstLetter() {
			// Prepare
			String expected = "Leadinguppercaseonly";
			when(attributeModel.getName()).thenReturn(expected);
			// Run & Check
			assertEquals(expected, unitUnderTest.getNameSeparated("_"));
		}

		@Test
		void returnsAnNameString_withASeparator_beforeEachUpperCaseLetter_exceptFirstLetter() {
			// Prepare
			String expected = "A_Camel_Case_Name";
			when(attributeModel.getName()).thenReturn("ACamelCaseName");
			// Run & Check
			assertEquals(expected, unitUnderTest.getNameSeparated("_"));
		}
	}
}
