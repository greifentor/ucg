package de.ollie.ucg.cli.yaml.model;

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

	@JsonProperty("classes")
	private List<YamlClassDefinition> classes;
}
