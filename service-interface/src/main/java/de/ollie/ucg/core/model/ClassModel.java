package de.ollie.ucg.core.model;

import java.util.List;
import lombok.Data;
import lombok.Generated;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
@ToString
public class ClassModel {

	private String name;
	private List<AttributeModel> attributes;
	private List<Property> properties;
}
