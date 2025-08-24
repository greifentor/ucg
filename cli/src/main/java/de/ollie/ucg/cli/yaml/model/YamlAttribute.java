package de.ollie.ucg.cli.yaml.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YamlAttribute {

	private String name;
	private YamlType type;

	@JsonProperty("enum")
	private boolean enumType;

	@JsonProperty("properties")
	private List<YamlProperty> properties;

	@JsonProperty("reference")
	private boolean reference;
}
