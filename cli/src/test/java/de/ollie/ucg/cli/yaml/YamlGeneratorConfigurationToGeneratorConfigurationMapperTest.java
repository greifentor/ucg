package de.ollie.ucg.cli.yaml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.ollie.ucg.cli.yaml.model.YamlProperty;
import de.ollie.ucg.cli.yaml.model.configuration.YamlGeneratorConfigurationWrapper;
import de.ollie.ucg.cli.yaml.model.configuration.YamlGeneratorSetting;
import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.model.GeneratorSetting.GeneratorType;
import de.ollie.ucg.core.model.Property;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class YamlGeneratorConfigurationToGeneratorConfigurationMapperTest {

	private static final String DEFAULT_TARGET_PATH = "default-target-path";
	private static final String PACKAGE_NAME = "package-name";
	private static final String PATH = "path";
	private static final String PROPERTY_NAME = "property-name";
	private static final String PROPERTY_VALUE = "property-value";
	private static final String TARGET_FILE_NAME = "targetFileName";
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
				.setDefaultTargetPath(DEFAULT_TARGET_PATH)
				.setGenerators(
					List.of(
						new YamlGeneratorSetting()
							.setPackageName(PACKAGE_NAME)
							.setPath(PATH)
							.setProperties(List.of(new YamlProperty(PROPERTY_NAME, PROPERTY_VALUE)))
							.setTargetFileName(DEFAULT_TARGET_PATH)
							.setTemplate(TEMPLATE)
							.setType(TYPE)
					)
				);
			GeneratorConfiguration expected = new GeneratorConfiguration()
				.setDefaultTargetPath(DEFAULT_TARGET_PATH)
				.setGeneratorSettings(
					List.of(
						new GeneratorSetting()
							.setGeneratorType(GeneratorType.CLASS)
							.setPackageName(PACKAGE_NAME)
							.setProperties(List.of(new Property().setName(PROPERTY_NAME).setValue(PROPERTY_VALUE)))
							.setResourceLoaderClass("org.apache.velocity.runtime.resource.loader.FileResourceLoader")
							.setTargetFileName(DEFAULT_TARGET_PATH)
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
