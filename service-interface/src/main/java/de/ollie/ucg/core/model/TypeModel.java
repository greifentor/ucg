package de.ollie.ucg.core.model;

import java.util.Map;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class TypeModel {

	private String name;
	private SimpleType simpleType;
	private ClassModel classType;
	private Map<String, Property> properties;
}
