package de.ollie.ucg.core.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class GeneratorConfiguration {

	private List<GeneratorSetting> generatorSettings;
	private List<Property> properties;
	private String defaultTargetPath;

	public Map<String, String> getPropertiesByNames() {
		Map<String, String> m = new HashMap<>();
		properties.stream().forEach(p -> m.put(p.getName(), "" + p.getValue()));
		return m;
	}
}
