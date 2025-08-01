package de.ollie.ucg.cli.yaml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.ollie.ucg.cli.yaml.model.YamlAttribute;
import de.ollie.ucg.cli.yaml.model.YamlClassDefinition;
import de.ollie.ucg.cli.yaml.model.YamlModel;
import de.ollie.ucg.cli.yaml.model.YamlProperty;
import de.ollie.ucg.cli.yaml.model.YamlType;
import de.ollie.ucg.core.model.AttributeModel;
import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.model.Property;
import de.ollie.ucg.core.model.TypeModel;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class YamlModelToModelMapperTest {

	private static final String ATTRIBUTE_NAME_0 = "attribute-name-0";
	private static final String ATTRIBUTE_TYPE_0 = "attribute-type-0";
	private static final String ATTRIBUTE_NAME_1 = "attribute-name-1";
	private static final String ATTRIBUTE_TYPE_1 = "attribute-type-1";
	private static final String CLASS_NAME = "class-name";
	private static final String IMPORT = "import";

	@InjectMocks
	private YamlModelToModelMapper unitUnderTest;

	@Nested
	class map_YamlModel {

		@Test
		void throwsAnException_passingANullValue_asYamlModel() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.map(null));
		}

		@Test
		void happyRun() {
			// Prepare
			Model expected = new Model()
				.setClasses(
					List.of(
						new ClassModel()
							.setName(CLASS_NAME)
							.setAttributes(
								List.of(
									new AttributeModel()
										.setName(ATTRIBUTE_NAME_0)
										.setProperties(List.of(new Property().setName("nullable").setValue("false")))
										.setReference(true)
										.setType(new TypeModel().setName(ATTRIBUTE_TYPE_0).addProperty("import", IMPORT)),
									new AttributeModel().setName(ATTRIBUTE_NAME_1).setType(new TypeModel().setName(ATTRIBUTE_TYPE_1))
								)
							)
							.setProperties(List.of())
					)
				);
			YamlModel passed = new YamlModel(
				List.of(
					new YamlClassDefinition(
						CLASS_NAME,
						List.of(
							new YamlAttribute(
								ATTRIBUTE_NAME_0,
								new YamlType(ATTRIBUTE_TYPE_0, List.of(new YamlProperty("import", IMPORT))),
								List.of(new YamlProperty("nullable", "false")),
								true
							),
							new YamlAttribute(ATTRIBUTE_NAME_1, new YamlType(ATTRIBUTE_TYPE_1, List.of()), List.of(), false)
						),
						List.of()
					)
				)
			);
			// Run
			Model returned = unitUnderTest.map(passed);
			// Check
			assertEquals(expected, returned);
		}
	}
}
