package de.ollie.ucg.template.processor.velocity.adapter.wrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import de.ollie.ucg.core.exception.GenerationFailedException;
import de.ollie.ucg.core.exception.GenerationFailedException.Type;
import de.ollie.ucg.core.model.AttributeModel;
import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.Property;
import de.ollie.ucg.core.model.TypeModel;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClassModelWrapperTest {

	private static final String ATTRIBUTE_NAME = "attribute-name";
	private static final String CLASS_NAME = "class-name";
	private static final String PROPERTY_NAME = "property-name";
	private static final String TYPE_NAME = "type-name";

	@Mock
	private AttributeModel attributeModel0;

	@Mock
	private AttributeModel attributeModel1;

	@Mock
	private ClassModel classModel;

	@Mock
	private TypeModel typeModel;

	@InjectMocks
	private ClassModelWrapper unitUnderTest;

	@Nested
	class constructor_ClassModel {

		@Test
		void throwsAnException_passingANullValue_asClassModel() {
			assertThrows(NullPointerException.class, () -> new ClassModelWrapper(null));
		}
	}

	@Nested
	class getAttributeTypeNameByPropertyName_String {

		@Test
		void throwsAnException_passingANullValue_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getAttributeTypeNameByPropertyName(null));
		}

		@Test
		void throwsAnException_whenNoAttributesIsSet() {
			// Prepare
			GenerationFailedException expected = new GenerationFailedException(
				CLASS_NAME,
				ATTRIBUTE_NAME,
				Type.NO_ATTRIBUTE_WITH_PROPERTY,
				List.of(new Property().setName("property").setValue(ATTRIBUTE_NAME))
			);
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0));
			when(attributeModel0.hasProperty(PROPERTY_NAME)).thenReturn(false);
			when(classModel.getName()).thenReturn(CLASS_NAME);
			// Run & Check
			assertThrows(
				GenerationFailedException.class,
				() -> unitUnderTest.getAttributeTypeNameByPropertyName(PROPERTY_NAME)
			);
		}

		@Test
		void returnsTrue_whenAnAttributeWithPassedNameIsSet_forAtLeastOneAttribute() {
			// Prepare
			when(attributeModel0.getType()).thenReturn(typeModel);
			when(attributeModel0.hasProperty(PROPERTY_NAME)).thenReturn(true);
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0));
			when(typeModel.getName()).thenReturn(TYPE_NAME);
			// Run & Check
			assertEquals(TYPE_NAME, unitUnderTest.getAttributeTypeNameByPropertyName(PROPERTY_NAME));
		}
	}

	@Nested
	class isPropertyWithNameInAttributesPresent_String {

		@Test
		void throwsAnException_passingANullValue_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.isPropertyWithNameInAttributesPresent(null));
		}

		@Test
		void returnsFalse_whenClassHasNoAttributes() {
			// Prepare
			when(classModel.getAttributes()).thenReturn(List.of());
			// Run & Check
			assertFalse(unitUnderTest.isPropertyWithNameInAttributesPresent(PROPERTY_NAME));
		}

		@Test
		void returnsFalse_whenClassAttributesHaveNoProperties() {
			// Prepare
			when(attributeModel0.getProperties()).thenReturn(List.of());
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0));
			// Run & Check
			assertFalse(unitUnderTest.isPropertyWithNameInAttributesPresent(PROPERTY_NAME));
		}

		@Test
		void returnsFalse_whenClassAttributesHaveProperties_butNoMatchingName() {
			// Prepare
			when(attributeModel0.getProperties()).thenReturn(List.of(new Property().setName(PROPERTY_NAME + 1)));
			when(attributeModel1.getProperties()).thenReturn(List.of(new Property().setName(PROPERTY_NAME + 2)));
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0, attributeModel1));
			// Run & Check
			assertFalse(unitUnderTest.isPropertyWithNameInAttributesPresent(PROPERTY_NAME));
		}

		@Test
		void returnsTrue_whenClassAttributesHaveProperties_andOneMatches() {
			// Prepare
			when(attributeModel0.getProperties()).thenReturn(List.of(new Property().setName(PROPERTY_NAME + 1)));
			when(attributeModel1.getProperties()).thenReturn(List.of(new Property().setName(PROPERTY_NAME)));
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0, attributeModel1));
			// Run & Check
			assertTrue(unitUnderTest.isPropertyWithNameInAttributesPresent(PROPERTY_NAME));
		}
	}
}
