package de.ollie.ucg.cli.yaml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.ollie.ucg.core.model.AttributeModel;
import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.model.Property;
import de.ollie.ucg.core.model.TypeModel;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class YamlModelReaderTest {

	private YamlModelReader unitUnderTest;

	@BeforeEach
	void beforeEach() {
		unitUnderTest = new YamlModelReader(new YamlModelToModelMapper());
	}

	@Nested
	class read_String {

		@Test
		void happyRun() throws Exception {
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
										.setProperties(List.of(new Property().setName("nullable").setValue("false")))
										.setType(new TypeModel().setName("UUID").addProperty("import", "java.util.UUID")),
									new AttributeModel().setName("title").setType(new TypeModel().setName("String"))
								)
							)
							.setProperties(List.of())
					)
				);
			// Run
			Model returned = unitUnderTest.read("src/test/resources/test-model.yml");
			// Check
			assertEquals(expected, returned);
		}
	}
}
