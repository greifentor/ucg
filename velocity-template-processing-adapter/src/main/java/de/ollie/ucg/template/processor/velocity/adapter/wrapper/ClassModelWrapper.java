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
import java.util.Optional;
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

	public List<AttributeModelWrapper> getAttributes() {
		return classModel.getAttributes().stream().map(a -> new AttributeModelWrapper(a, model)).toList();
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
					List.of(new Property().setName("property").setValue("id"))
				)
			);
	}

	private Optional<AttributeModel> findAttributeWithProperty(String name) {
		return classModel.getAttributes().stream().filter(a -> a.hasProperty(name)).findFirst();
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
					List.of(new Property().setName("property").setValue("id"))
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
		ensure(name != null, MSG_NAME_IS_NULL);
		return classModel
			.getAttributes()
			.stream()
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
