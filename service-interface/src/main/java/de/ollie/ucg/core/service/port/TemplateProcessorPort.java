package de.ollie.ucg.core.service.port;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.EnumModel;
import de.ollie.ucg.core.model.GenerationResult;
import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.model.Model;

public interface TemplateProcessorPort {
	GenerationResult process(
		GeneratorConfiguration generatorConfiguration,
		GeneratorSetting generatorSetting,
		Model model,
		ClassModel classModel
	);
	GenerationResult process(
		GeneratorConfiguration generatorConfiguration,
		GeneratorSetting generatorSetting,
		Model model,
		EnumModel enumModel
	);
	GenerationResult process(
		GeneratorConfiguration generatorConfiguration,
		GeneratorSetting generatorSetting,
		Model model
	);
}
