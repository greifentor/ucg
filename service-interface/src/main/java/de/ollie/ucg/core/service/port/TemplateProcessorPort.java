package de.ollie.ucg.core.service.port;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.GeneratorSetting;

public interface TemplateProcessorPort {
	String process(GeneratorSetting generatorSetting, ClassModel classModel);
}
