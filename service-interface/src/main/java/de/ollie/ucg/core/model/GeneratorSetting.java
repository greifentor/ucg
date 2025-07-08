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

	private String templateFileName;
	private String templatePath;
	private String subProjectName;
	private String packageName;
	private GeneratorType generatorType;
}
