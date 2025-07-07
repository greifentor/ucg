package de.ollie.ucg.core.service.port;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.GeneratorConfiguration;

public interface TemplateProcessorPort {
	String process(GeneratorConfiguration generatorConfiguration, ClassModel classModel);
}
