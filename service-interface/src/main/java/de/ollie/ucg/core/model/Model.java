package de.ollie.ucg.core.model;

import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class Model {

	private Map<String, ClassModel> classes;
	private Map<String, Property> properties;
	private List<GeneratorConfiguration> generatorConfigurations;
}
