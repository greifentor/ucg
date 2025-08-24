package de.ollie.ucg.template.processor.velocity.adapter.wrapper;

import de.ollie.ucg.core.model.Property;
import de.ollie.ucg.core.model.PropertyOwner;
import java.util.List;
import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Generated
@Getter
@RequiredArgsConstructor
public class ReferenceWrapper implements Comparable<ReferenceWrapper>, PropertyOwner {

	private final String typeName;
	private final List<PropertyWrapper> propertyWrappers;

	@Override
	public List<Property> getProperties() {
		return propertyWrappers.stream().map(pw -> new Property(pw.getName(), pw.getValue())).toList();
	}

	@Override
	public int compareTo(ReferenceWrapper o) {
		return getTypeName().compareTo(o.getTypeName());
	}
}
