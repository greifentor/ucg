package de.ollie.ucg.cli.yaml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.ollie.ucg.cli.yaml.model.configuration.YamlGeneratorConfigurationWrapper;
import de.ollie.ucg.cli.yaml.model.configuration.YamlGeneratorSetting;
import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.model.GeneratorSetting.GeneratorType;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class YamlGeneratorConfigurationToGeneratorConfigurationMapperTest {

	private static final String PACKAGE_NAME = "package-name";
	private static final String PATH = "path";
	private static final String TEMPLATE = "template";
	private static final String TYPE = "CLASS";

	@InjectMocks
	private YamlGeneratorConfigurationToGeneratorConfigurationMapper unitUnderTest;

	@Nested
	class map_YamlGeneratorConfigurationWrapper {

		@Test
		void throwsAnException_passingANulLValue_asYamlGeneratorConfigurationWrapper() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.map(null));
		}

		@Test
		void happyRun() {
			// Prepare
			YamlGeneratorConfigurationWrapper passed = new YamlGeneratorConfigurationWrapper()
				.setGenerators(
					List.of(
						new YamlGeneratorSetting().setPackageName(PACKAGE_NAME).setPath(PATH).setTemplate(TEMPLATE).setType(TYPE)
					)
				);
			GeneratorConfiguration expected = new GeneratorConfiguration()
				.setGeneratorSettings(
					List.of(
						new GeneratorSetting()
							.setGeneratorType(GeneratorType.CLASS)
							.setPackageName(PACKAGE_NAME)
							.setResourceLoaderClass("org.apache.velocity.runtime.resource.loader.FileResourceLoader")
							.setTemplateFileName(TEMPLATE)
							.setTemplatePath(PATH)
					)
				);
			// Run
			GeneratorConfiguration returned = unitUnderTest.map(passed);
			// Check
			assertEquals(expected, returned);
		}
	}
}
