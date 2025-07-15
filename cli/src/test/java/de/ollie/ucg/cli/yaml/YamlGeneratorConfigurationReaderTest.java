package de.ollie.ucg.cli.yaml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.model.GeneratorSetting.GeneratorType;
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
				.setGeneratorSettings(
					List.of(
						new GeneratorSetting()
							.setGeneratorType(GeneratorType.CLASS)
							.setPackageName("de.library.core.service.model")
							.setResourceLoaderClass("org.apache.velocity.runtime.resource.loader.FileResourceLoader")
							.setTemplateFileName("Model.vc")
							.setTemplatePath("velocity-template-processing-adapter/src/main/resources/templates")
					)
				);
			// Run
			GeneratorConfiguration returned = unitUnderTest.read("src/test/resources/test-settings.yml");
			// Check
			assertEquals(expected, returned);
		}
	}
}
