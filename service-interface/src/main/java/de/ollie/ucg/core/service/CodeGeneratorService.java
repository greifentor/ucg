package de.ollie.ucg.core.service;

import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.model.Report;

public interface CodeGeneratorService {
	Report generate(Model model);
}
