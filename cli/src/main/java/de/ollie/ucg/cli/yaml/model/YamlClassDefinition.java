package de.ollie.ucg.cli.yaml.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Generated
@NoArgsConstructor
public class YamlClassDefinition {

	private String name;

	@JsonProperty("attributes")
	private List<YamlAttribute> attributes;

	@JsonProperty("properties")
	private List<YamlProperty> properties;
}
