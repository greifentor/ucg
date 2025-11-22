package de.ollie.ucg.template.processor.velocity.adapter.wrapper;

import de.ollie.baselib.util.NameSeparator;
import de.ollie.ucg.core.model.EnumModel;
import de.ollie.ucg.core.model.Model;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EnumModelWrapper {

	@NonNull
	private final EnumModel enumModel;

	@NonNull
	private final Model model;

	public List<String> getIdentifiers() {
		return enumModel.getIdentifiers();
	}

	public String getName() {
		return enumModel.getName();
	}

	public String getNameCamelCase() {
		return toCamelCase(getName());
	}

	public String getCamelCaseNameSeparated(String separator) {
		return NameSeparator.INSTANCE.getNameSeparated(getNameCamelCase(), separator);
	}

	public String toCamelCase(String s) {
		return s.length() < 2 ? s.toUpperCase() : s.substring(0, 1).toUpperCase() + s.substring(1);
	}
}
