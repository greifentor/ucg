package de.ollie.ucg.yaml;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.yaml.model.YamlModel;
import jakarta.inject.Named;
import java.io.File;
import java.io.IOException;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

@Generated
@Named
@RequiredArgsConstructor
public class YamlModelReader {

	private final YamlModelToModelMapper modelMapper;

	public Model read(String fileName) throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		YamlModel yamlModel = mapper.readValue(new File(fileName), YamlModel.class);
		return modelMapper.map(yamlModel);
	}
}
