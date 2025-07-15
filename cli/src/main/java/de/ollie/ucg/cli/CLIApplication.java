package de.ollie.ucg.cli;

import de.ollie.ucg.cli.yaml.YamlGeneratorConfigurationReader;
import de.ollie.ucg.cli.yaml.YamlModelReader;
import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.service.CodeGeneratorService;
import de.ollie.ucg.core.service.CodeGeneratorService.CodeGeneratorServiceObserver;
import jakarta.inject.Inject;
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

	@Inject
	private YamlGeneratorConfigurationReader yamlGeneratorConfigurationReader;

	@Inject
	private YamlModelReader yamlModelReader;

	public static void main(String[] args) {
		SpringApplication.run(CLIApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		GeneratorConfiguration configuration = null;
		if (!args.getOptionValues("config").isEmpty()) {
			configuration = yamlGeneratorConfigurationReader.read(args.getOptionValues("config").get(0));
		} else {
			throw new IllegalStateException("No generator configuration defined!");
		}
		if (!args.getOptionValues("model").isEmpty()) {
			Model model = yamlModelReader.read(args.getOptionValues("model").get(0));
			codeGeneratorService.generate(model, configuration, this);
		} else {
			throw new IllegalStateException("No class model defined!");
		}
	}

	@Override
	public void classCodeGenerated(String classCode) {
		System.out.println(classCode);
	}
}
