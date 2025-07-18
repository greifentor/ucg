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
	private List<Property> properties = new ArrayList<>();

	public boolean hasProperty(String name) {
		ensure(name != null, "name cannot be null!");
		return properties.stream().anyMatch(a -> name.equals(a.getName()));
	}
}
