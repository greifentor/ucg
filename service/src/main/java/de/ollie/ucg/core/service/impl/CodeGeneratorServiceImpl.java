package de.ollie.ucg.core.service.impl;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.model.GeneratorSetting.GeneratorType;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.model.Report;
import de.ollie.ucg.core.service.CodeGeneratorService;
import de.ollie.ucg.core.service.port.TemplateProcessorPort;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class CodeGeneratorServiceImpl implements CodeGeneratorService {

	private final ReportFactory reportFactory;
	private final TemplateProcessorPort templateProcessorPort;

	@Override
	public Report generate(Model model, GeneratorConfiguration configuration, CodeGeneratorServiceObserver observer) {
		ensure(model != null, "model cannot be null!");
		ensure(observer != null, "observer cannot be null!");
		Report report = reportFactory.create();
		for (GeneratorSetting cs : configuration.getGeneratorSettings()) {
			if (cs.getGeneratorType() == GeneratorType.CLASS) {
				for (ClassModel classModel : model.getClasses()) {
					String classCode = templateProcessorPort.process(cs, classModel);
					observer.classCodeGenerated(classCode);
				}
			}
		}
		return report;
	}
}
