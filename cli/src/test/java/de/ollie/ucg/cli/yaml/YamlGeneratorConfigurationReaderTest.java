package de.ollie.ucg.cli.yaml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.model.GeneratorSetting.GeneratorType;
import de.ollie.ucg.core.model.Property;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class YamlGeneratorConfigurationReaderTest {

	private YamlGeneratorConfigurationReader unitUnderTest;

	@BeforeEach
	void beforeEach() {
		unitUnderTest =
			new YamlGeneratorConfigurationReader(new YamlGeneratorConfigurationToGeneratorConfigurationMapper());
	}

	@Nested
	class read_String {

		@Test
		void happyRun() throws Exception {
			// Prepare
			GeneratorConfiguration expected = new GeneratorConfiguration()
				.setDefaultTargetPath("tmp")
				.setGeneratorSettings(
					List.of(
						new GeneratorSetting()
							.setGeneratorType(GeneratorType.CLASS)
							.setPackageName("de.library.core.service.model")
							.setProperties(List.of(new Property().setName("target-path").setValue("tmp0")))
							.setResourceLoaderClass("org.apache.velocity.runtime.resource.loader.FileResourceLoader")
							.setTargetFileName("${UnitName}Dbo")
							.setTemplateFileName("Model.vc")
							.setTemplatePath("velocity-template-processing-adapter/src/main/resources/templates")
					)
				)
				.setProperties(List.of(new Property().setName("general-property-name").setValue("general-property-value")));
			// Run
			GeneratorConfiguration returned = unitUnderTest.read("src/test/resources/test-settings.yml");
			// Check
			assertEquals(expected, returned);
		}
	}
}
