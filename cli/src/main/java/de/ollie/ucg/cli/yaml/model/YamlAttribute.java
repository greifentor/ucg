package de.ollie.ucg.cli.yaml.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YamlAttribute {

	private String name;
	private YamlType type;
}
