package de.ollie.ucg.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.model.GeneratorSetting.GeneratorType;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.model.Report;
import de.ollie.ucg.core.service.CodeGeneratorService.CodeGeneratorServiceObserver;
import de.ollie.ucg.core.service.port.TemplateProcessorPort;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CodeGeneratorServiceImplTest {

	private static final String CLASS_NAME = "class-name";
	private static final String CLASS_CODE = "class-text";

	@Mock
	private ClassModel classModel;

	@Mock
	private CodeGeneratorServiceObserver observer;

	@Mock
	private GeneratorConfiguration generatorConfiguration;

	@Mock
	private GeneratorSetting generatorSetting;

	@Mock
	private Model model;

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
			when(model.getClasses()).thenReturn(Map.of(CLASS_NAME, classModel));
			when(generatorConfiguration.getGeneratorSettings()).thenReturn(List.of(generatorSetting));
			when(reportFactory.create()).thenReturn(report);
			when(templateProcessingPort.process(generatorSetting, classModel)).thenReturn(CLASS_CODE);
			// Run & Check
			assertEquals(report, unitUnderTest.generate(model, generatorConfiguration, observer));
		}

		@Test
		void callsTheFileSystemPortCorrectly() {
			// Prepare
			when(generatorSetting.getGeneratorType()).thenReturn(GeneratorType.CLASS);
			when(model.getClasses()).thenReturn(Map.of(CLASS_NAME, classModel));
			when(generatorConfiguration.getGeneratorSettings()).thenReturn(List.of(generatorSetting));
			when(reportFactory.create()).thenReturn(report);
			when(templateProcessingPort.process(generatorSetting, classModel)).thenReturn(CLASS_CODE);
			// Run
			unitUnderTest.generate(model, generatorConfiguration, observer);
			// Check
			verify(observer, times(1)).classCodeGenerated(CLASS_CODE);
		}
	}
}
