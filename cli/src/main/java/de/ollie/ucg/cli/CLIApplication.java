package de.ollie.ucg.cli;

import de.ollie.ucg.cli.yaml.YamlGeneratorConfigurationReader;
import de.ollie.ucg.cli.yaml.YamlModelReader;
import de.ollie.ucg.core.model.GenerationResult;
import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.service.CodeGeneratorService;
import de.ollie.ucg.core.service.CodeGeneratorService.CodeGeneratorServiceObserver;
import de.ollie.ucg.core.service.SettingMappingService;
import jakarta.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans({ @ComponentScan("de.ollie.ucg"), @ComponentScan("de.ollie.baselib") })
public class CLIApplication implements ApplicationRunner, CodeGeneratorServiceObserver {

	@Inject
	private CodeGeneratorService codeGeneratorService;

	@Inject
	private SettingMappingService settingMappingService;

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
	public void codeGenerated(
		GenerationResult generationResult,
		GeneratorSetting generatorSetting,
		GeneratorConfiguration configuration
	) {
		String path =
			getTargetPath(configuration, generatorSetting) +
			"/" +
			settingMappingService.map(generatorSetting.getPackageName(), generationResult.getUnitName()).replace(".", "/");
		String fileName =
			path +
			"/" +
			settingMappingService.map(generatorSetting.getTargetFileName(), generationResult.getUnitName()) +
			".java";
		try {
			boolean write = true;
			if (new File(fileName).exists()) {
				write = Files.readString(Path.of(fileName)).contains(CodeGeneratorService.GENERATED_CODE_MARKER);
			}
			if (write) {
				Files.createDirectories(Path.of(path));
				Files.writeString(
					Path.of(fileName),
					generationResult.getCode(),
					StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING
				);
				System.out.println("wrote: " + fileName);
			} else {
				System.out.println("suppressed: " + fileName);
			}
		} catch (IOException ioe) {
			System.out.println("\n\nError while writing file: " + fileName);
			System.out.println("\n\nMessage: " + ioe.getMessage());
		}
	}

	private String getTargetPath(GeneratorConfiguration configuration, GeneratorSetting setting) {
		return setting.getPropertyByName("target-path").orElseGet(configuration::getDefaultTargetPath);
	}
}
