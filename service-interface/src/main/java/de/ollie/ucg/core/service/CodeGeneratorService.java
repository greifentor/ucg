package de.ollie.ucg.core.service;

import de.ollie.ucg.core.model.GenerationResult;
import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.model.Report;

public interface CodeGeneratorService {
	public interface CodeGeneratorServiceObserver {
		void classCodeGenerated(
			GenerationResult generationResult,
			GeneratorSetting generatorSetting,
			GeneratorConfiguration configuration
		);
	}

	Report generate(Model model, GeneratorConfiguration configuration, CodeGeneratorServiceObserver observer);
}
