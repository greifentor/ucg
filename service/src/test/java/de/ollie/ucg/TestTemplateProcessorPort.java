package de.ollie.ucg;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.GenerationResult;
import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.service.port.TemplateProcessorPort;
import jakarta.inject.Named;

@Named
class TestTemplateProcessorPort implements TemplateProcessorPort {

	@Override
	public GenerationResult process(
		GeneratorConfiguration generatorConfiguration,
		GeneratorSetting generatorSetting,
		Model model,
		ClassModel classModel
	) {
		// TODO Auto-generated method stub
		return null;
	}
}
