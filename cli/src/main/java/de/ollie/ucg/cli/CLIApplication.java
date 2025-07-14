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
					new GeneratorSetting()
						.setGeneratorType(GeneratorType.CLASS)
						.setPackageName("de.ollie.healthtracker.coder.service.model")
						.setTemplatePath("velocity-template-processing-adapter/src/main/resources")
						.setTemplateFileName("templates/Model.vc")
				)
			);
		Model model = new Model()
			.setClasses(
				List.of(
					new ClassModel()
						.setName("DoctorType")
						.setAttributes(
							List.of(
								new AttributeModel()
									.setName("id")
									.setType(new TypeModel().setName("UUID").addProperty("import", "java.util.UUID")),
								new AttributeModel().setName("name").setType(new TypeModel().setName("String"))
							)
						)
				)
			);
		codeGeneratorService.generate(model, configuration, this);
	}

	@Override
	public void classCodeGenerated(String classCode) {
		System.out.println(classCode);
	}
}
