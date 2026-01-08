package de.ollie.ucg.template.processor.velocity.adapter.wrapper;

import de.ollie.baselib.util.NameSeparator;
import de.ollie.ucg.core.model.AttributeModel;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.model.Property;
import java.util.List;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
public class AttributeModelWrapper {

	@NonNull
	private final AttributeModel attributeModel;

	@NonNull
	private final Model model;

	public String getName() {
		return attributeModel.getName();
	}

	public String getDefaultValue() {
		return attributeModel.getDefaultValue();
	}

	public String getNameSeparated(String separator) {
		return NameSeparator.INSTANCE.getNameSeparated(getName(), separator);
	}

	public String getCamelCaseNameSeparated(String separator) {
		return NameSeparator.INSTANCE.getNameSeparated(getNameCamelCase(), separator);
	}

	public String getTypeName() {
		return attributeModel.getType().getName();
	}

	public boolean isTypeEnum() {
		return attributeModel.isEnumType();
	}

	private static final Set<String> SIMPLE_TYPE_NAMES = Set.of(
		"boolean",
		"byte",
		"char",
		"double",
		"float",
		"int",
		"long",
		"short"
	);

	public boolean isSimpleType() {
		return SIMPLE_TYPE_NAMES.contains(getTypeName());
	}

	public String getPropertyValue(String name) {
		return attributeModel.getPropertyValue(name);
	}

	public List<Property> getProperties() {
		return attributeModel.getProperties();
	}

	public ClassModelWrapper getTypeClassType() {
		return attributeModel.getType().getClassType() != null
			? new ClassModelWrapper(attributeModel.getType().getClassType(), model)
			: null;
	}

	public boolean hasProperty(String name) {
		return attributeModel.hasProperty(name);
	}

	public boolean hasPropertyWithValue(String name, String value) {
		return attributeModel.hasPropertyWithValue(name, value);
	}

	public boolean hasTypePropertyWithValue(String name, String value) {
		return attributeModel.getType().hasPropertyWithValue(name, value);
	}

	public boolean isReference() {
		return attributeModel.isReference();
	}

	public String getNameCamelCase() {
		return getName().length() < 2
			? getName().toUpperCase()
			: getName().substring(0, 1).toUpperCase() + getName().substring(1);
	}
}
