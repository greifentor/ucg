package de.ollie.ucg.core.model;

import static de.ollie.baselib.util.Check.ensure;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class TypeModel implements PropertyOwner {

	private String name;
	private SimpleType simpleType;
	private ClassModel classType;
	private List<Property> properties = new ArrayList<>();

	public TypeModel addProperty(String name, Object value) {
		ensure(name != null, "name cannot be null!");
		ensure(value != null, "value cannot be null!");
		properties.add(new Property(name, "" + value));
		return this;
	}

	public boolean hasProperty(String name) {
		ensure(name != null, "name cannot be null!");
		return properties.stream().anyMatch(a -> name.equals(a.getName()));
	}
}
