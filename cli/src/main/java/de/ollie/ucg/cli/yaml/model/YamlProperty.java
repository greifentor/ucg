package de.ollie.ucg.cli.yaml.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YamlProperty {

	private String name;
	private String value;
}
