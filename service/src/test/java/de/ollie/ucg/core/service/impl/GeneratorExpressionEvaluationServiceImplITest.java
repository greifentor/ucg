package de.ollie.ucg.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.model.Property;
import de.ollie.ucg.core.upn.command.HasProperty;
import jakarta.inject.Inject;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GeneratorExpressionEvaluationServiceImplITest {

	private static final String PROPERTY_NAME = "property-name";

	@Inject
	private GeneratorExpressionEvaluationServiceImpl unitUnderTest;

	@Test
	void returnsFalse_whenExpressionIsEvaluatedAsFalse() {
		// Prepare
		ClassModel classModel = new ClassModel().setProperties(List.of(new Property().setName(PROPERTY_NAME + 1)));
		GeneratorSetting generatorSetting = new GeneratorSetting()
			.setGenerateWhen("classModel Load " + PROPERTY_NAME + " " + HasProperty.TOKEN);
		// Run & Check
		assertFalse(unitUnderTest.suppressGeneratorForClassModel(classModel, generatorSetting));
	}
}
