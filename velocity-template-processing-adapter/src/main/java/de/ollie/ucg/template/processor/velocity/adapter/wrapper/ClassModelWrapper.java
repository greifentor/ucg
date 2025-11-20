package de.ollie.ucg.template.processor.velocity.adapter.wrapper;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.baselib.util.NameSeparator;
import de.ollie.ucg.core.exception.GenerationFailedException;
import de.ollie.ucg.core.exception.GenerationFailedException.Type;
import de.ollie.ucg.core.model.AttributeModel;
import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.model.Property;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClassModelWrapper {

	private static final String MSG_NAME_IS_NULL = "name cannot be null!";

	@NonNull
	private final ClassModel classModel;

	@NonNull
	private final Model model;

	public String getName() {
		return classModel.getName();
	}

	public String getNameCamelCase() {
		return toCamelCase(classModel.getName());
	}

	public String getCamelCaseNameSeparated(String separator) {
		return NameSeparator.INSTANCE.getNameSeparated(getNameCamelCase(), separator);
	}

	public String getNameAsAttribute() {
		return toAttributeName(classModel.getName());
	}

	public String toAttributeName(String s) {
		return s.length() < 2 ? s.toLowerCase() : s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	public List<AttributeModelWrapper> getAttributes() {
		return classModel.getAttributes().stream().map(a -> new AttributeModelWrapper(a, model)).toList();
	}

	public AttributeModelWrapper getAttributeByName(String name) {
		ensure(name != null, MSG_NAME_IS_NULL);
		return classModel
			.getAttributes()
			.stream()
			.filter(a -> name.equals(a.getName()))
			.findFirst()
			.map(a -> new AttributeModelWrapper(a, model))
			.orElse(null);
	}

	public String getAttributeTypeNameByPropertyName(String name) {
		ensure(name != null, MSG_NAME_IS_NULL);
		return findAttributeWithProperty(name)
			.map(a -> a.getType().getName())
			.orElseThrow(() ->
				new GenerationFailedException(
					getName(),
					null,
					Type.NO_ATTRIBUTE_WITH_PROPERTY,
					List.of(new Property().setName("property").setValue(name))
				)
			);
	}

	private Optional<AttributeModel> findAttributeWithProperty(String name) {
		return classModel.getAttributes().stream().filter(a -> a.hasProperty(name)).findFirst();
	}

	public AttributeModel getAttributeWithProperty(String name) {
		return findAttributeWithProperty(name)
			.orElseThrow(() -> new NoSuchElementException("class has no attribute with property:" + name));
	}

	public String getAttributeTypeNameByPropertyNameCamelCase(String name) {
		ensure(name != null, MSG_NAME_IS_NULL);
		return findAttributeWithProperty(name)
			.map(a -> toCamelCase(a.getType().getName()))
			.orElseThrow(() ->
				new GenerationFailedException(
					getName(),
					null,
					Type.NO_ATTRIBUTE_WITH_PROPERTY,
					List.of(new Property().setName("property").setValue("id"))
				)
			);
	}

	public String toCamelCase(String s) {
		return s.length() < 2 ? s.toUpperCase() : s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public String getAttributeNameByTypePropertyName(String name) {
		ensure(name != null, MSG_NAME_IS_NULL);
		return findAttributeWithProperty(name)
			.map(a -> a.getName())
			.orElseThrow(() ->
				new GenerationFailedException(
					getName(),
					null,
					Type.NO_ATTRIBUTE_WITH_PROPERTY,
					List.of(new Property().setName("property").setValue(name))
				)
			);
	}

	public String getAttributeNameByTypePropertyNameCamelCase(String name) {
		ensure(name != null, MSG_NAME_IS_NULL);
		return findAttributeWithProperty(name)
			.map(a -> toCamelCase(a.getName()))
			.orElseThrow(() ->
				new GenerationFailedException(
					getName(),
					null,
					Type.NO_ATTRIBUTE_WITH_PROPERTY,
					List.of(new Property().setName("property").setValue("id"))
				)
			);
	}

	public List<AttributeModelWrapper> getAttributesWithPropertyNotSet(String name) {
		ensure(name != null, MSG_NAME_IS_NULL);
		return classModel
			.getAttributes()
			.stream()
			// .filter(a -> !a.getProperties().isEmpty())
			.filter(a -> !a.hasProperty(name))
			.map(a -> new AttributeModelWrapper(a, model))
			.toList();
	}

	public List<AttributeModelWrapper> getAttributesWithPropertySet(String name) {
		ensure(name != null, MSG_NAME_IS_NULL);
		return classModel
			.getAttributes()
			.stream()
			.filter(a -> !a.getProperties().isEmpty())
			.filter(a -> a.hasProperty(name))
			.map(a -> new AttributeModelWrapper(a, model))
			.toList();
	}

	public List<AttributeModelWrapper> getAttributesWithPropertySetSortedByPropertyValue(String name) {
		return getAttributesWithPropertySetSortedByPropertyValue(name, a -> true);
	}

	public List<AttributeModelWrapper> getAttributesWithPropertySetSortedByPropertyValueReferencesOnly(String name) {
		return getAttributesWithPropertySetSortedByPropertyValue(name, AttributeModel::isReference);
	}

	public List<AttributeModelWrapper> getAttributesWithPropertySetSortedByPropertyValue(
		String name,
		Predicate<AttributeModel> gate
	) {
		ensure(name != null, MSG_NAME_IS_NULL);
		return classModel
			.getAttributes()
			.stream()
			.filter(gate::test)
			.filter(a -> !a.getProperties().isEmpty())
			.filter(a -> a.hasProperty(name))
			.sorted((a0, a1) -> a0.getPropertyValue(name).compareTo(a1.getPropertyValue(name)))
			.map(a -> new AttributeModelWrapper(a, model))
			.toList();
	}

	public List<PropertyWrapper> getPropertiesWithName(String name) {
		ensure(name != null, MSG_NAME_IS_NULL);
		return classModel.getProperties() == null
			? List.of()
			: classModel
				.getProperties()
				.stream()
				.filter(p -> name.equals(p.getName()))
				.map(p -> new PropertyWrapper(p.getName(), "" + p.getValue()))
				.toList();
	}

	public String getAttributeNameWithPropertySeparated(String name) {
		return NameSeparator.INSTANCE.getNameSeparated(getAttributeTypeNameByPropertyName(name), "_");
	}

	public String getNameSeparated(String separator) {
		return NameSeparator.INSTANCE.getNameSeparated(classModel.getName(), separator);
	}

	public boolean hasAReferenceAttribute() {
		return classModel.getAttributes().stream().anyMatch(AttributeModel::isReference);
	}

	public boolean hasAnEnumTypeAttribute() {
		return classModel.getAttributes().stream().anyMatch(AttributeModel::isEnumType);
	}

	public boolean hasAnAttributeWithTypeName(String typeName) {
		ensure(typeName != null, "type name cannot be null!");
		return classModel.getAttributes().stream().anyMatch(a -> a.getType().getName().equals(typeName));
	}

	public boolean hasAnAttributeWithPropertyAndValue(String propertyName, String propertyValue) {
		return classModel.getAttributes().stream().anyMatch(p -> p.hasPropertyWithValue(propertyName, propertyValue));
	}

	public boolean isPropertyWithNameInAttributesPresent(String name) {
		ensure(name != null, MSG_NAME_IS_NULL);
		return classModel.getAttributes().isEmpty()
			? false
			: getAllProperties().stream().anyMatch(p -> name.equals(p.getName()));
	}

	private List<Property> getAllProperties() {
		return classModel.getAttributes().stream().flatMap(a -> a.getProperties().stream()).toList();
	}

	public List<Property> getAllPropertiesByName(String name) {
		return classModel.getProperties().stream().filter(p -> p.getName().equals(name)).toList();
	}
}
