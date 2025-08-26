package de.ollie.ucg.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import de.ollie.ucg.core.exception.GenerationFailedException.Type;
import de.ollie.ucg.core.model.Property;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GenerationFailedExceptionTest {

	private static final String CLASS_NAME = "className";
	private static final String ATTRIBUTE_NAME = "attributeName";
	private static final Type TYPE = Type.NO_ATTRIBUTE_WITH_PROPERTY;

	@Nested
	class constructor_String_String_Type_ListProperty {

		@Test
		void setsAttributeNameCorrectly() {
			assertEquals(
				ATTRIBUTE_NAME,
				new GenerationFailedException(CLASS_NAME, ATTRIBUTE_NAME, TYPE, List.of()).getAttributeName()
			);
		}

		@Test
		void setsClassNameCorrectly() {
			assertEquals(
				CLASS_NAME,
				new GenerationFailedException(CLASS_NAME, ATTRIBUTE_NAME, TYPE, List.of()).getClassName()
			);
		}

		@Test
		void setsPropertyListCorrectly() {
			// Prepare
			List<Property> list = List.of();
			// Run & Check
			assertSame(list, new GenerationFailedException(CLASS_NAME, ATTRIBUTE_NAME, TYPE, list).getProperties());
		}

		@Test
		void setsTypeCorrectly() {
			assertEquals(TYPE, new GenerationFailedException(CLASS_NAME, ATTRIBUTE_NAME, TYPE, List.of()).getType());
		}
	}
}
