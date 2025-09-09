package de.ollie.ucg.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.service.port.UpnEvaluationPort;
import java.util.Stack;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GeneratorExpressionEvaluationServiceImplTest {

	private static final String EXPRESSION = "expression";

	@Mock
	private ClassModel classModel;

	@Mock
	private GeneratorSetting generatorSetting;

	@Mock
	private UpnEvaluationPort upnEvaluationPort;

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

		@Test
		void returnsFalse_whenGeneratorSettting_generateWhen_isEvaluated_withNoResult() {
			// Prepare
			Stack<Object> stack = new Stack<>();
			when(generatorSetting.getGenerateWhen()).thenReturn(EXPRESSION);
			when(upnEvaluationPort.evaluate(EXPRESSION, classModel)).thenReturn(stack);
			// Run & Check
			assertFalse(unitUnderTest.suppressGeneratorForClassModel(classModel, generatorSetting));
		}

		@Test
		void returnsFalse_whenGeneratorSettting_generateWhen_isEvaluated_withResultNotBoolean() {
			// Prepare
			Stack<Object> stack = new Stack<>();
			stack.push(";op");
			when(generatorSetting.getGenerateWhen()).thenReturn(EXPRESSION);
			when(upnEvaluationPort.evaluate(EXPRESSION, classModel)).thenReturn(stack);
			// Run & Check
			assertTrue(unitUnderTest.suppressGeneratorForClassModel(classModel, generatorSetting));
		}

		@Test
		void returnsTrue_whenGeneratorSettting_generateWhen_isEvaluated_withResultFalse() {
			// Prepare
			Stack<Object> stack = new Stack<>();
			stack.push(Boolean.FALSE);
			when(generatorSetting.getGenerateWhen()).thenReturn(EXPRESSION);
			when(upnEvaluationPort.evaluate(EXPRESSION, classModel)).thenReturn(stack);
			// Run & Check
			assertTrue(unitUnderTest.suppressGeneratorForClassModel(classModel, generatorSetting));
		}

		@Test
		void returnsFalse_whenGeneratorSettting_generateWhen_isEvaluated_withResultTrue() {
			// Prepare
			Stack<Object> stack = new Stack<>();
			stack.push(Boolean.TRUE);
			when(generatorSetting.getGenerateWhen()).thenReturn(EXPRESSION);
			when(upnEvaluationPort.evaluate(EXPRESSION, classModel)).thenReturn(stack);
			// Run & Check
			assertFalse(unitUnderTest.suppressGeneratorForClassModel(classModel, generatorSetting));
		}
	}
}
