package de.ollie.ucg.core.model;

import java.util.List;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class GeneratorConfiguration {

	private List<GeneratorSetting> generatorSettings;
}
