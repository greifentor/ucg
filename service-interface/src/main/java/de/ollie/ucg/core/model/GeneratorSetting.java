package de.ollie.ucg.core.model;

import static de.ollie.baselib.util.Check.ensure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class GeneratorSetting {

	public enum GeneratorType {
		CLASS,
		MODEL,
	}

	private GeneratorType generatorType;
	private String packageName;
	private List<Property> properties = new ArrayList<>();
	private String resourceLoaderClass;
	private String subProjectName;
	private String templateFileName;
	private String templatePath;

	public Optional<String> getPropertyByName(String propertyName) {
		ensure(propertyName != null, "property name cannot be null!");
		return properties.stream().filter(p -> propertyName.equals(p.getName())).map(p -> "" + p.getValue()).findFirst();
	}
}
