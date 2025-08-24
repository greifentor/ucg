package de.ollie.ucg.template.processor.velocity.adapter.wrapper;

import de.ollie.ucg.core.model.AttributeModel;
import de.ollie.ucg.core.model.Model;
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

	public String getNameSeparated(String separator) {
		return NameSeparator.INSTANCE.getNameSeparated(attributeModel.getName(), separator);
	}

	public String getTypeName() {
		return attributeModel.getType().getName();
	}

	public boolean isTypeEnum() {
		return attributeModel.isEnumType();
	}

	public ClassModelWrapper getTypeClassType() {
		return attributeModel.getType().getClassType() != null
			? new ClassModelWrapper(attributeModel.getType().getClassType(), model)
			: null;
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
