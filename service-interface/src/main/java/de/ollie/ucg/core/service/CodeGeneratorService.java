package de.ollie.ucg.core.service;

import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.model.Report;

public interface CodeGeneratorService {
	public interface CodeGeneratorServiceObserver {
		void classCodeGenerated(String classCode);
	}

	Report generate(Model model, CodeGeneratorServiceObserver observer);
}
