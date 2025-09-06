package de.ollie.ucg.core.model;

import static de.ollie.baselib.util.Check.ensure;

import java.util.List;

public interface PropertyOwner {
	List<Property> getProperties();

	default boolean hasPropertyWithValue(String name, String value) {
		ensure(name != null, "name cannot be null!");
		return (getProperties() == null) || getProperties().isEmpty()
			? false
			: getProperties()
				.stream()
				.filter(p -> name.equals(p.getName()))
				.anyMatch(p -> (value != null) && (value.equals(p.getValue())));
	}

	default boolean hasPropertyWithName(String name) {
		ensure(name != null, "name cannot be null!");
		return (getProperties() == null) || getProperties().isEmpty()
			? false
			: getProperties().stream().anyMatch(p -> name.equals(p.getName()));
	}
}
