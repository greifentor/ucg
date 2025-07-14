package de.ollie.ucg.cli.yaml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.ollie.ucg.cli.yaml.model.YamlAttribute;
import de.ollie.ucg.cli.yaml.model.YamlAttributeWrapper;
import de.ollie.ucg.cli.yaml.model.YamlClassDefinition;
import de.ollie.ucg.cli.yaml.model.YamlClassWrapper;
import de.ollie.ucg.cli.yaml.model.YamlModel;
import de.ollie.ucg.cli.yaml.model.YamlProperty;
import de.ollie.ucg.cli.yaml.model.YamlPropertyWrapper;
import de.ollie.ucg.cli.yaml.model.YamlType;
import de.ollie.ucg.core.model.AttributeModel;
import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.model.TypeModel;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class YamlModelToModelMapperTest {

	@InjectMocks
	private YamlModelToModelMapper unitUnderTest;

	@Nested
	class map_YamlModel {

		@Test
		void happyRun() {
			// Prepare
			Model expected = new Model()
				.setClasses(
					List.of(
						new ClassModel()
							.setName("Book")
							.setAttributes(
								List.of(
									new AttributeModel()
										.setName("id")
										.setType(new TypeModel().setName("UUID").addProperty("import", "java.util.UUID")),
									new AttributeModel().setName("title").setType(new TypeModel().setName("String"))
								)
							)
					)
				);
			YamlModel passed = new YamlModel(
				List.of(
					new YamlClassWrapper(
						new YamlClassDefinition(
							"Book",
							List.of(
								new YamlAttributeWrapper(
									new YamlAttribute(
										"id",
										new YamlType("UUID", List.of(new YamlPropertyWrapper(new YamlProperty("import", "java.util.UUID"))))
									)
								),
								new YamlAttributeWrapper(new YamlAttribute("title", new YamlType("String", List.of())))
							)
						)
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
