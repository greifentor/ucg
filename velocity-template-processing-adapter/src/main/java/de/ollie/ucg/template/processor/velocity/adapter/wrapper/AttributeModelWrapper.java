package de.ollie.ucg.template.processor.velocity.adapter.wrapper;

import de.ollie.ucg.core.model.AttributeModel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
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
}
