package de.ollie.ucg.core.model;

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
	private String resourceLoaderClass;
	private String subProjectName;
	private String templateFileName;
	private String templatePath;
}
