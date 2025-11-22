package de.ollie.ucg.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.EnumModel;
import de.ollie.ucg.core.model.GenerationResult;
import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.model.GeneratorSetting.GeneratorType;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.model.Report;
import de.ollie.ucg.core.service.CodeGeneratorService.CodeGeneratorServiceObserver;
import de.ollie.ucg.core.service.GeneratorExpressionEvaluationService;
import de.ollie.ucg.core.service.port.TemplateProcessorPort;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CodeGeneratorServiceImplTest {

	@Mock
	private CodeGeneratorServiceObserver observer;

	@Mock
	private GeneratorConfiguration generatorConfiguration;

	@Mock
	private GeneratorExpressionEvaluationService generatorExpressionEvaluationService;

	@Mock
	private GenerationResult generationResult;

	@Mock
	private GeneratorSetting generatorSetting;

	@Mock
	private Report report;

	@Mock
	private ReportFactory reportFactory;

	@Mock
	private TemplateProcessorPort templateProcessingPort;

	@InjectMocks
	private CodeGeneratorServiceImpl unitUnderTest;

	@Nested
	class generate_Model {

		@Mock
		private ClassModel classModel;

		@Mock
		private EnumModel enumModel;

		@Mock
		private Model model;

		@Test
		void throwsAnException_passingANullValueAsModel() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.generate(null, generatorConfiguration, observer)
			);
		}

		@Test
		void throwsAnException_passingANullValueAsObserver() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.generate(model, generatorConfiguration, null));
		}

		@Test
		void callsTheTemplateProcessingPortCorrectly() {
			// Prepare
			when(generatorSetting.getGeneratorType()).thenReturn(GeneratorType.CLASS);
			when(model.getClasses()).thenReturn(List.of(classModel));
			when(generatorConfiguration.getGeneratorSettings()).thenReturn(List.of(generatorSetting));
			when(reportFactory.create()).thenReturn(report);
			when(templateProcessingPort.process(generatorConfiguration, generatorSetting, model, classModel))
				.thenReturn(generationResult);
			// Run & Check
			assertEquals(report, unitUnderTest.generate(model, generatorConfiguration, observer));
		}

		@Test
		void callsTheFileSystemPortCorrectly_forClasses() {
			// Prepare
			when(generatorSetting.getGeneratorType()).thenReturn(GeneratorType.CLASS);
			when(model.getClasses()).thenReturn(List.of(classModel));
			when(generatorConfiguration.getGeneratorSettings()).thenReturn(List.of(generatorSetting));
			when(reportFactory.create()).thenReturn(report);
			when(templateProcessingPort.process(generatorConfiguration, generatorSetting, model, classModel))
				.thenReturn(generationResult);
			// Run
			unitUnderTest.generate(model, generatorConfiguration, observer);
			// Check
			verify(observer, times(1)).codeGenerated(generationResult, generatorSetting, generatorConfiguration);
		}

		@Test
		void skipsGenerationWhen_GeneratorExpressionEvaluationService_isToGenerate_returnsFalse() {
			// Prepare
			String layer = "layer";
			when(generatorSetting.getGeneratorType()).thenReturn(GeneratorType.CLASS);
			when(generatorSetting.getLayer()).thenReturn(layer);
			when(model.getClasses()).thenReturn(List.of(classModel));
			when(generatorConfiguration.getGeneratorSettings()).thenReturn(List.of(generatorSetting));
			when(reportFactory.create()).thenReturn(report);
			when(classModel.findPropertyValue("ignore-layers")).thenReturn(Optional.of(layer));
			// Run
			unitUnderTest.generate(model, generatorConfiguration, observer);
			// Check
			verifyNoInteractions(observer, templateProcessingPort);
		}

		@Test
		void callsTheFileSystemPortCorrectly_forEnums() {
			// Prepare
			when(generatorSetting.getGeneratorType()).thenReturn(GeneratorType.ENUM);
			when(generatorConfiguration.getGeneratorSettings()).thenReturn(List.of(generatorSetting));
			when(model.getEnums()).thenReturn(List.of(enumModel));
			when(reportFactory.create()).thenReturn(report);
			when(templateProcessingPort.process(generatorConfiguration, generatorSetting, model, enumModel))
				.thenReturn(generationResult);
			// Run
			unitUnderTest.generate(model, generatorConfiguration, observer);
			// Check
			verify(observer, times(1)).codeGenerated(generationResult, generatorSetting, generatorConfiguration);
		}
	}
}
