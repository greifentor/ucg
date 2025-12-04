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
public class AttributeModel implements PropertyOwner {

	private String name;
	private TypeModel type;
	private String defaultValue;
	private List<Property> properties = new ArrayList<>();
	private boolean enumType;
	private boolean reference;

	public boolean hasProperty(String name) {
		ensure(name != null, "name cannot be null!");
		return properties.stream().anyMatch(p -> name.equals(p.getName()));
	}

	public String getPropertyValue(String name) {
		ensure(name != null, "name cannot be null!");
		return properties.stream().filter(p -> name.equals(p.getName())).map(Property::getValue).findFirst().orElse(null);
	}
}
