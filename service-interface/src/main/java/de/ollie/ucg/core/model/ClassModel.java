package de.ollie.ucg.core.model;

import java.util.Map;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class ClassModel {

	private String name;
	private Map<String, AttributeModel> attributes;
	private Map<String, Property> properties;
}
