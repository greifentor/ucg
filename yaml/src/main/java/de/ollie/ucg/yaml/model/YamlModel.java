package de.ollie.ucg.yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Generated
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class YamlModel {

	private String title;

	@JsonProperty("classes")
	private List<YamlClassDefinition> classes;

	@JsonProperty("enums")
	private List<YamlEnumDefinition> enums;

	@JsonProperty("properties")
	private List<YamlProperty> properties;
}
