package de.ollie.ucg.core.service.impl;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.model.GeneratorConfiguration.GeneratorType;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.model.Report;
import de.ollie.ucg.core.service.CodeGeneratorService;
import de.ollie.ucg.core.service.port.FileSystemPort;
import de.ollie.ucg.core.service.port.TemplateProcessingPort;
import jakarta.inject.Named;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class CodeGeneratorServiceImpl implements CodeGeneratorService {

	private final FileSystemPort fileSystemPort;
	private final ReportFactory reportFactory;
	private final TemplateProcessingPort templateProcessingPort;

	@Override
	public Report generate(Model model) {
		ensure(model != null, "model cannot be null!");
		Report report = reportFactory.create();
		for (GeneratorConfiguration cg : model.getGeneratorConfigurations()) {
			if (cg.getGeneratorType() == GeneratorType.CLASS) {
				for (Entry<String, ClassModel> entry : model.getClasses().entrySet()) {
					String classText = templateProcessingPort.process(cg, entry.getValue());
					fileSystemPort.storeClassFile(cg, classText);
				}
			}
		}
		return report;
	}
}
