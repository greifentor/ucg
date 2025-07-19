package de.ollie.ucg.cli.yaml.model.configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.ollie.ucg.cli.yaml.model.YamlProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class YamlGeneratorConfigurationWrapper {

	@JsonProperty("default-target-path")
	private String defaultTargetPath;

	@JsonProperty("generators")
	private List<YamlGeneratorSetting> generators;

	@JsonProperty("properties")
	private List<YamlProperty> properties = new ArrayList<>();
}
