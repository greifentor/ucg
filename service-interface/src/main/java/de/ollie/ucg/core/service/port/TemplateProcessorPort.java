package de.ollie.ucg.core.service.port;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.GenerationResult;
import de.ollie.ucg.core.model.GeneratorSetting;

public interface TemplateProcessorPort {
	GenerationResult process(GeneratorSetting generatorSetting, ClassModel classModel);
}
