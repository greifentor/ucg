package de.ollie.ucg.cli;

import de.ollie.ucg.core.model.AttributeModel;
import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.model.GeneratorSetting.GeneratorType;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.model.TypeModel;
import de.ollie.ucg.core.service.CodeGeneratorService;
import de.ollie.ucg.core.service.CodeGeneratorService.CodeGeneratorServiceObserver;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("de.ollie.ucg")
public class CLIApplication implements ApplicationRunner, CodeGeneratorServiceObserver {

	@Inject
	private CodeGeneratorService codeGeneratorService;

	public static void main(String[] args) {
		SpringApplication.run(CLIApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		GeneratorConfiguration configuration = new GeneratorConfiguration()
			.setGeneratorSettings(
				List.of(
					new GeneratorSetting().setGeneratorType(GeneratorType.CLASS),
					new GeneratorSetting()
						.setTemplatePath("velocity-template-processing-adapter/src/test/resources/templates")
						.setTemplateFileName("TestTemplate.vc")
				)
			);
		Model model = new Model()
			.setClasses(
				Map.of(
					"Test",
					new ClassModel()
						.setName("Test")
						.setAttributes(Map.of("id", new AttributeModel().setName("id").setType(new TypeModel().setName("long"))))
				)
			);
		codeGeneratorService.generate(model, configuration, this);
	}

	@Override
	public void classCodeGenerated(String classCode) {
		System.out.println(classCode);
	}
}
