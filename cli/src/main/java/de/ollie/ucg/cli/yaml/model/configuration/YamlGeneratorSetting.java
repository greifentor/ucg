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
public class YamlGeneratorSetting {

	@JsonProperty("type")
	private String type;

	@JsonProperty("generate-when")
	private String generateWhen;

	@JsonProperty("properties")
	private List<YamlProperty> properties = new ArrayList<>();

	@JsonProperty("package")
	private String packageName;

	@JsonProperty("path")
	private String path;

	@JsonProperty("resource-class-loader")
	private String resourceClassLoader = "org.apache.velocity.runtime.resource.loader.FileResourceLoader";

	@JsonProperty("target-file-name")
	private String targetFileName = "${UnitName}";

	@JsonProperty("template")
	private String template;
}
