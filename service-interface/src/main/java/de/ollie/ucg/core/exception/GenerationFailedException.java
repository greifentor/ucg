package de.ollie.ucg.core.exception;

import de.ollie.ucg.core.model.Property;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;

@Generated
@Getter
@EqualsAndHashCode(callSuper = false)
public class GenerationFailedException extends RuntimeException {

	@Generated
	public enum Type {
		NO_ATTRIBUTE_WITH_PROPERTY(),
	}

	private final String className;
	private final String attributeName;
	private final Type type;
	private final List<Property> properties;

	public GenerationFailedException(String className, String attributeName, Type type, List<Property> properties) {
		super();
		this.className = className;
		this.attributeName = attributeName;
		this.type = type;
		this.properties = properties;
	}
}
