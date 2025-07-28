package de.ollie.ucg.cli.yaml.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YamlType {

	private String name;

	@JsonProperty("properties")
	private List<YamlProperty> properties;
}
