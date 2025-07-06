package de.ollie.ucg.core.model;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class GeneratorConfiguration {

	public enum GeneratorType {
		CLASS,
		MODEL,
	}

	private String templateFileName;
	private String subProjectName;
	private String packageName;
	private GeneratorType generatorType;
}
