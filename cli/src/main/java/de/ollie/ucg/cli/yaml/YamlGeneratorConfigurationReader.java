package de.ollie.ucg.cli.yaml;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.ollie.ucg.cli.yaml.model.configuration.YamlGeneratorConfigurationWrapper;
import de.ollie.ucg.core.model.GeneratorConfiguration;
import jakarta.inject.Named;
import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class YamlGeneratorConfigurationReader {

	private final YamlGeneratorConfigurationToGeneratorConfigurationMapper configurationMapper;

	public GeneratorConfiguration read(String fileName) throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		YamlGeneratorConfigurationWrapper yamlGeneratorConfigration = mapper.readValue(
			new File(fileName),
			YamlGeneratorConfigurationWrapper.class
		);
		return configurationMapper.map(yamlGeneratorConfigration);
	}
}
