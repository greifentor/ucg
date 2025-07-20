package de.ollie.ucg.template.processor.velocity.adapter.wrapper;

import de.ollie.ucg.core.model.AttributeModel;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
public class AttributeModelWrapper {

	@NonNull
	private final AttributeModel model;

	public String getName() {
		return model.getName();
	}

	public String getTypeName() {
		return model.getType().getName();
	}

	public boolean hasPropertyWithValue(String name, String value) {
		return model.hasPropertyWithValue(name, value);
	}

	public String getNameSeparated(String separator) {
		return NameSeparator.INSTANCE.getNameSeparated(model.getName(), separator);
	}
}
