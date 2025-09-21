package de.ollie.ucg.template.processor.velocity.adapter.wrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import de.ollie.ucg.core.exception.GenerationFailedException;
import de.ollie.ucg.core.exception.GenerationFailedException.Type;
import de.ollie.ucg.core.model.AttributeModel;
import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.Model;
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
	private static final String ATTRIBUTE_NAME_CAMEL_CASE = "Attribute-name";
	private static final String CLASS_NAME = "class-name";
	private static final String PROPERTY_NAME = "property-name";
	private static final String PROPERTY_VALUE = "property-value";
	private static final String TYPE_NAME = "type-name";
	private static final String TYPE_NAME_CAMEL_CASE = "Type-name";

	@Mock
	private AttributeModel attributeModel0;

	@Mock
	private AttributeModel attributeModel1;

	@Mock
	private ClassModel classModel;

	@Mock
	private Model model;

	@Mock
	private Property property;

	@Mock
	private TypeModel typeModel;

	@InjectMocks
	private ClassModelWrapper unitUnderTest;

	@Nested
	class constructor_ClassModel {

		@Test
		void throwsAnException_passingANullValue_asClassModel() {
			assertThrows(NullPointerException.class, () -> new ClassModelWrapper(null, model));
		}

		@Test
		void throwsAnException_passingANullValue_asModel() {
			assertThrows(NullPointerException.class, () -> new ClassModelWrapper(classModel, null));
		}
	}

	@Nested
	class getAttributeByName_String {

		@Test
		void throwsAnException_passingANullValue_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getAttributeByName(null));
		}

		@Test
		void returnsANullValue_whenClassHasNoAttributes() {
			assertNull(unitUnderTest.getAttributeByName(ATTRIBUTE_NAME));
		}

		@Test
		void returnsANullValue_passingANotExistingAttributeName() {
			// Prepare
			when(attributeModel0.getName()).thenReturn(ATTRIBUTE_NAME + 1);
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0));
			// Run & Check
			assertNull(unitUnderTest.getAttributeByName(ATTRIBUTE_NAME));
		}

		@Test
		void returnsTheCorrectAttribute_passingAnExistingAttributeName() {
			// Prepare
			when(attributeModel0.getName()).thenReturn(ATTRIBUTE_NAME);
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0));
			// Run & Check
			assertEquals(new AttributeModelWrapper(attributeModel0, model), unitUnderTest.getAttributeByName(ATTRIBUTE_NAME));
		}
	}

	@Nested
	class getAttributes {

		@Test
		void returnsAnEmptyList_whenClassModelHasNoAttributes() {
			// Prepare
			when(classModel.getAttributes()).thenReturn(List.of());
			// Run & Check
			assertTrue(unitUnderTest.getAttributes().isEmpty());
		}

		@Test
		void returnsAListWithAttributeWrappers_whenClassModelHasAttributes() {
			// Prepare
			List<AttributeModelWrapper> expected = List.of(new AttributeModelWrapper(attributeModel0, model));
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0));
			// Run & Check
			assertEquals(expected, unitUnderTest.getAttributes());
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
				null,
				Type.NO_ATTRIBUTE_WITH_PROPERTY,
				List.of(new Property().setName("property").setValue(PROPERTY_NAME))
			);
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0));
			when(attributeModel0.hasProperty(PROPERTY_NAME)).thenReturn(false);
			when(classModel.getName()).thenReturn(CLASS_NAME);
			// Run & Check
			Exception thrown = assertThrows(
				GenerationFailedException.class,
				() -> unitUnderTest.getAttributeTypeNameByPropertyName(PROPERTY_NAME)
			);
			assertEquals(expected, thrown);
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
	class getAttributeTypeNameByPropertyNameCamelCase_String {

		@Test
		void throwsAnException_passingANullValue_asName() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.getAttributeTypeNameByPropertyNameCamelCase(null)
			);
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
				() -> unitUnderTest.getAttributeTypeNameByPropertyNameCamelCase(PROPERTY_NAME)
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
			assertEquals(TYPE_NAME_CAMEL_CASE, unitUnderTest.getAttributeTypeNameByPropertyNameCamelCase(PROPERTY_NAME));
		}
	}

	@Nested
	class getAttributesWithPropertyNotSet_String {

		@Test
		void throwsAnException_passingANullValue_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getAttributesWithPropertyNotSet(null));
		}

		@Test
		void returnsAnEmptyList_whenClassHasNoAttributes() {
			// Prepare
			when(classModel.getAttributes()).thenReturn(List.of());
			// Run & Check
			assertTrue(unitUnderTest.getAttributesWithPropertyNotSet(PROPERTY_NAME).isEmpty());
		}

		@Test
		void returnsAnEmptyList_whenClassHasAttributeWithNoProperties() {
			// Prepare
			AttributeModel a = new AttributeModel();
			when(classModel.getAttributes()).thenReturn(List.of(a));
			// Run & Check
			assertEquals(
				List.of(new AttributeModelWrapper(a, model)),
				unitUnderTest.getAttributesWithPropertyNotSet(PROPERTY_NAME)
			);
		}

		@Test
		void returnsAnEmptyList_whenClassModelHasAttributesWithMatchingPropertySetOnly() {
			// Prepare
			when(classModel.getAttributes())
				.thenReturn(List.of(new AttributeModel().setProperties(List.of(new Property().setName(PROPERTY_NAME)))));
			// Run & Check
			assertEquals(List.of(), unitUnderTest.getAttributesWithPropertyNotSet(PROPERTY_NAME));
		}

		@Test
		void returnsAListWithAttributeWrapper_whenClassModelHasAttributesWithNotMatchingPropertySet() {
			// Prepare
			AttributeModel attribute0 = new AttributeModel().setProperties(List.of(new Property().setName(PROPERTY_NAME)));
			AttributeModel attribute1 = new AttributeModel()
				.setProperties(List.of(new Property().setName(PROPERTY_NAME + 1)));
			List<AttributeModelWrapper> expected = List.of(new AttributeModelWrapper(attribute1, model));
			when(classModel.getAttributes()).thenReturn(List.of(attribute0, attribute1));
			// Run & Check
			assertEquals(expected, unitUnderTest.getAttributesWithPropertyNotSet(PROPERTY_NAME));
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
			when(classModel.getName()).thenReturn(expected);
			// Run & Check
			assertEquals(expected, unitUnderTest.getNameSeparated("_"));
		}

		@Test
		void returnsAnUnchangedNameString_whenNameContainsLowerCaseLetterOnly_exceptFirstLetter() {
			// Prepare
			String expected = "Leadinguppercaseonly";
			when(classModel.getName()).thenReturn(expected);
			// Run & Check
			assertEquals(expected, unitUnderTest.getNameSeparated("_"));
		}

		@Test
		void returnsAnNameString_withASeparator_beforeEachUpperCaseLetter_exceptFirstLetter() {
			// Prepare
			String expected = "A_Camel_Case_Name";
			when(classModel.getName()).thenReturn("ACamelCaseName");
			// Run & Check
			assertEquals(expected, unitUnderTest.getNameSeparated("_"));
		}
	}

	@Nested
	class hasAnAttributeWithTypeName_String {

		@Test
		void throwsAnException_passingTypeNameAsANullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.hasAnAttributeWithTypeName(null));
		}

		@Test
		void returnsFalse_whenClassHasNoAttributeWithPassedTypeName() {
			// Prepare
			when(attributeModel0.getType()).thenReturn(typeModel);
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0));
			when(typeModel.getName()).thenReturn(TYPE_NAME + 1);
			// Run & Check
			assertFalse(unitUnderTest.hasAnAttributeWithTypeName(TYPE_NAME));
		}

		@Test
		void returnsTrue_whenClassHasAnAttributeWithPassedTypeName() {
			// Prepare
			when(attributeModel0.getType()).thenReturn(typeModel);
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0));
			when(typeModel.getName()).thenReturn(TYPE_NAME);
			// Run & Check
			assertTrue(unitUnderTest.hasAnAttributeWithTypeName(TYPE_NAME));
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

	@Nested
	class getAttributeNameByTypePropertyName_String {

		@Test
		void throwsAnException_passingANullValue_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getAttributeNameByTypePropertyName(null));
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
				() -> unitUnderTest.getAttributeNameByTypePropertyName(PROPERTY_NAME)
			);
		}

		@Test
		void returnsTrue_whenAnAttributeWithPassedNameIsSet_forAtLeastOneAttribute() {
			// Prepare
			when(attributeModel0.getName()).thenReturn(ATTRIBUTE_NAME);
			when(attributeModel0.hasProperty(PROPERTY_NAME)).thenReturn(true);
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0));
			// Run & Check
			assertEquals(ATTRIBUTE_NAME, unitUnderTest.getAttributeNameByTypePropertyName(PROPERTY_NAME));
		}
	}

	@Nested
	class getAttributeNameByTypePropertyNameCamelCase_String {

		@Test
		void throwsAnException_passingANullValue_asName() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.getAttributeNameByTypePropertyNameCamelCase(null)
			);
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
				() -> unitUnderTest.getAttributeNameByTypePropertyNameCamelCase(PROPERTY_NAME)
			);
		}

		@Test
		void returnsTrue_whenAnAttributeWithPassedNameIsSet_forAtLeastOneAttribute() {
			// Prepare
			when(attributeModel0.getName()).thenReturn(ATTRIBUTE_NAME);
			when(attributeModel0.hasProperty(PROPERTY_NAME)).thenReturn(true);
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0));
			// Run & Check
			assertEquals(ATTRIBUTE_NAME_CAMEL_CASE, unitUnderTest.getAttributeNameByTypePropertyNameCamelCase(PROPERTY_NAME));
		}
	}

	@Nested
	class getAttributesWithPropertySet_String {

		@Test
		void throwsAnException_passingANullValueAsName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getAttributesWithPropertySet(null));
		}

		@Test
		void returnsAnEmptyList_withClassModel_hasNoAttributes() {
			// Prepare
			when(classModel.getAttributes()).thenReturn(List.of());
			// Run & Check
			assertTrue(unitUnderTest.getAttributesWithPropertySet(PROPERTY_NAME).isEmpty());
		}

		@Test
		void returnsAnEmptyList_withClassModel_hasAttributeWithNoProperties() {
			// Prepare
			when(attributeModel0.getProperties()).thenReturn(List.of());
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0));
			// Run & Check
			assertTrue(unitUnderTest.getAttributesWithPropertySet(PROPERTY_NAME).isEmpty());
		}

		@Test
		void returnsAnEmptyList_withClassModel_hasAttributeWithNoMatchingProperty() {
			// Prepare
			when(attributeModel0.getProperties()).thenReturn(List.of(property));
			when(attributeModel0.hasProperty(PROPERTY_NAME)).thenReturn(false);
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0));
			// Run & Check
			assertTrue(unitUnderTest.getAttributesWithPropertySet(PROPERTY_NAME).isEmpty());
		}

		@Test
		void returnsAListWithMatchingAttributeModelWrapper_classModel_hasAttributeWithMatchingProperties() {
			// Prepare
			List<AttributeModelWrapper> expected = List.of(new AttributeModelWrapper(attributeModel0, model));
			when(attributeModel0.getProperties()).thenReturn(List.of(property));
			when(attributeModel0.hasProperty(PROPERTY_NAME)).thenReturn(true);
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0));
			// Run & Check
			assertEquals(expected, unitUnderTest.getAttributesWithPropertySet(PROPERTY_NAME));
		}
	}

	@Nested
	class getAttributesWithPropertySetSortedByPropertyValue_String {

		@Test
		void throwsAnException_passingANullValueAsName() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.getAttributesWithPropertySetSortedByPropertyValue(null)
			);
		}

		@Test
		void returnsAnEmptyList_withClassModel_hasNoAttributes() {
			// Prepare
			when(classModel.getAttributes()).thenReturn(List.of());
			// Run & Check
			assertTrue(unitUnderTest.getAttributesWithPropertySetSortedByPropertyValue(PROPERTY_NAME).isEmpty());
		}

		@Test
		void returnsAnEmptyList_withClassModel_hasAttributeWithNoProperties() {
			// Prepare
			when(attributeModel0.getProperties()).thenReturn(List.of());
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0));
			// Run & Check
			assertTrue(unitUnderTest.getAttributesWithPropertySetSortedByPropertyValue(PROPERTY_NAME).isEmpty());
		}

		@Test
		void returnsAnEmptyList_withClassModel_hasAttributeWithNoMatchingProperty() {
			// Prepare
			when(attributeModel0.getProperties()).thenReturn(List.of(property));
			when(attributeModel0.hasProperty(PROPERTY_NAME)).thenReturn(false);
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0));
			// Run & Check
			assertTrue(unitUnderTest.getAttributesWithPropertySetSortedByPropertyValue(PROPERTY_NAME).isEmpty());
		}

		@Test
		void returnsAListWithMatchingAttributeModelWrapper_classModel_hasAttributeWithMatchingProperties() {
			// Prepare
			List<AttributeModelWrapper> expected = List.of(
				new AttributeModelWrapper(attributeModel0, model),
				new AttributeModelWrapper(attributeModel1, model)
			);
			when(attributeModel0.getPropertyValue(PROPERTY_NAME)).thenReturn("0");
			when(attributeModel0.getProperties()).thenReturn(List.of(property));
			when(attributeModel0.hasProperty(PROPERTY_NAME)).thenReturn(true);
			when(attributeModel1.getPropertyValue(PROPERTY_NAME)).thenReturn("1");
			when(attributeModel1.getProperties()).thenReturn(List.of(property));
			when(attributeModel1.hasProperty(PROPERTY_NAME)).thenReturn(true);
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel1, attributeModel0));
			// Run & Check
			assertEquals(expected, unitUnderTest.getAttributesWithPropertySetSortedByPropertyValue(PROPERTY_NAME));
		}
	}

	@Nested
	class getPropertiesWithName_String {

		@Test
		void throwsAnException_passingANullValue_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getPropertiesWithName(null));
		}

		@Test
		void returnsAnEmptyList_whenClassModel_hasANullPointer_asPropertyList() {
			// Prepare
			when(classModel.getProperties()).thenReturn(null);
			// Run & Check
			assertTrue(unitUnderTest.getPropertiesWithName(PROPERTY_NAME).isEmpty());
		}

		@Test
		void returnsAnEmptyList_whenClassModel_hasAnEmptyList_asPropertyList() {
			// Prepare
			when(classModel.getProperties()).thenReturn(List.of());
			// Run & Check
			assertTrue(unitUnderTest.getPropertiesWithName(PROPERTY_NAME).isEmpty());
		}

		@Test
		void returnsAnEmptyList_whenClassModel_hasAList_withNoMatchingProperty() {
			// Prepare
			when(classModel.getProperties()).thenReturn(List.of(property));
			when(property.getName()).thenReturn(PROPERTY_NAME + 1);
			// Run & Check
			assertTrue(unitUnderTest.getPropertiesWithName(PROPERTY_NAME).isEmpty());
		}

		@Test
		void returnsTheListWithMatchingPropertyWrapper_whenClassModel_hasAList_withMatchingProperty() {
			// Prepare
			List<PropertyWrapper> expected = List.of(new PropertyWrapper(PROPERTY_NAME, PROPERTY_VALUE));
			when(classModel.getProperties()).thenReturn(List.of(property));
			when(property.getName()).thenReturn(PROPERTY_NAME);
			when(property.getValue()).thenReturn(PROPERTY_VALUE);
			// Run & Check
			assertEquals(expected, unitUnderTest.getPropertiesWithName(PROPERTY_NAME));
		}
	}

	@Nested
	class toCamelCase_String {

		@Test
		void throwsAnException_passingANullValue_asString() {
			assertThrows(NullPointerException.class, () -> unitUnderTest.toCamelCase(null));
		}

		@Test
		void returnsAnEmptyString_passingAnEmptyString() {
			assertEquals("", unitUnderTest.toCamelCase(""));
		}

		@Test
		void returnsThePassedString_passingACamelCaseString() {
			assertEquals(TYPE_NAME_CAMEL_CASE, unitUnderTest.toCamelCase(TYPE_NAME_CAMEL_CASE));
		}

		@Test
		void returnsACamelCaseString_passingANonCamelCaseString() {
			assertEquals(TYPE_NAME_CAMEL_CASE, unitUnderTest.toCamelCase(TYPE_NAME));
		}
	}

	@Nested
	class getAttributeNameWithPropertySeparated_String {

		@Test
		void throwsAnException_passingANullValue_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getAttributeNameWithPropertySeparated(null));
		}

		@Test
		void throwsAnException_whenNoAttributesIsSet() {
			// Prepare
			GenerationFailedException expected = new GenerationFailedException(
				CLASS_NAME,
				null,
				Type.NO_ATTRIBUTE_WITH_PROPERTY,
				List.of(new Property().setName("property").setValue(PROPERTY_NAME))
			);
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0));
			when(attributeModel0.hasProperty(PROPERTY_NAME)).thenReturn(false);
			when(classModel.getName()).thenReturn(CLASS_NAME);
			// Run & Check
			Exception thrown = assertThrows(
				GenerationFailedException.class,
				() -> unitUnderTest.getAttributeNameWithPropertySeparated(PROPERTY_NAME)
			);
			assertEquals(expected, thrown);
		}

		@Test
		void returnsTrue_whenAnAttributeWithPassedNameIsSet_forAtLeastOneAttribute() {
			// Prepare
			when(attributeModel0.getType()).thenReturn(typeModel);
			when(attributeModel0.hasProperty(PROPERTY_NAME)).thenReturn(true);
			when(classModel.getAttributes()).thenReturn(List.of(attributeModel0));
			when(typeModel.getName()).thenReturn(TYPE_NAME);
			// Run & Check
			assertEquals(TYPE_NAME, unitUnderTest.getAttributeNameWithPropertySeparated(PROPERTY_NAME));
		}
	}
}
