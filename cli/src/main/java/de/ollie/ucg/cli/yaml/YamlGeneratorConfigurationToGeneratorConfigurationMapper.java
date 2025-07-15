package de.ollie.ucg.cli.yaml;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.ucg.cli.yaml.model.configuration.YamlGeneratorConfigurationWrapper;
import de.ollie.ucg.cli.yaml.model.configuration.YamlGeneratorSetting;
import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.model.GeneratorSetting.GeneratorType;
import jakarta.inject.Named;
import java.util.List;

@Named
class YamlGeneratorConfigurationToGeneratorConfigurationMapper {

	GeneratorConfiguration map(YamlGeneratorConfigurationWrapper yamlGeneratorConfigurationWrapper) {
		ensure(yamlGeneratorConfigurationWrapper != null, "YAML generator configuration wrapper cannot be null!");
		return new GeneratorConfiguration()
			.setGeneratorSettings(getGeneratorSettings(yamlGeneratorConfigurationWrapper.getGenerators()));
	}

	private List<GeneratorSetting> getGeneratorSettings(List<YamlGeneratorSetting> yamlSettings) {
		return yamlSettings.stream().map(this::getSetting).toList();
	}

	private GeneratorSetting getSetting(YamlGeneratorSetting yamlSetting) {
		return new GeneratorSetting()
			.setGeneratorType(getGeneratorType(yamlSetting.getType()))
			.setPackageName(yamlSetting.getPackageName())
			.setResourceLoaderClass(yamlSetting.getResourceClassLoader())
			.setTemplateFileName(yamlSetting.getTemplate())
			.setTemplatePath(yamlSetting.getPath());
	}

	private GeneratorType getGeneratorType(String name) {
		return GeneratorType.valueOf(name);
	}
}
