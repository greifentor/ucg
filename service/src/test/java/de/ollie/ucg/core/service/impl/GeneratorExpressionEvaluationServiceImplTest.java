package de.ollie.ucg.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.GeneratorSetting;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GeneratorExpressionEvaluationServiceImplTest {

	@Mock
	private ClassModel classModel;

	@Mock
	private GeneratorSetting generatorSetting;

	@InjectMocks
	private GeneratorExpressionEvaluationServiceImpl unitUnderTest;

	@Nested
	class suppressGeneratorForClassModel_ClassModel_GeneratorSetting {

		@Test
		void throwsAnException_passingANullValueAsClassModel() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.suppressGeneratorForClassModel(null, generatorSetting)
			);
		}

		@Test
		void throwsAnException_passingANullValueAsGeneratorSetting() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.suppressGeneratorForClassModel(classModel, null)
			);
		}

		@Test
		void returnsFalse_whenGeneratorSettting_generateWhen_isNull() {
			// Prepare
			when(generatorSetting.getGenerateWhen()).thenReturn(null);
			// Run & Check
			assertFalse(unitUnderTest.suppressGeneratorForClassModel(classModel, generatorSetting));
		}
	}
}
