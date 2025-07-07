package de.ollie.ucg.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.model.GeneratorConfiguration.GeneratorType;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.model.Report;
import de.ollie.ucg.core.service.CodeGeneratorService.CodeGeneratorServiceObserver;
import de.ollie.ucg.core.service.port.TemplateProcessingPort;
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
	private Model model;

	@Mock
	private Report report;

	@Mock
	private ReportFactory reportFactory;

	@Mock
	private TemplateProcessingPort templateProcessingPort;

	@InjectMocks
	private CodeGeneratorServiceImpl unitUnderTest;

	@Nested
	class generate_Model {

		@Test
		void throwsAnException_passingANullValueAsModel() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.generate(null, observer));
		}

		@Test
		void throwsAnException_passingANullValueAsObserver() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.generate(model, null));
		}

		@Test
		void callsTheTemplateProcessingPortCorrectly() {
			// Prepare
			when(generatorConfiguration.getGeneratorType()).thenReturn(GeneratorType.CLASS);
			when(model.getClasses()).thenReturn(Map.of(CLASS_NAME, classModel));
			when(model.getGeneratorConfigurations()).thenReturn(List.of(generatorConfiguration));
			when(reportFactory.create()).thenReturn(report);
			when(templateProcessingPort.process(generatorConfiguration, classModel)).thenReturn(CLASS_CODE);
			// Run
			unitUnderTest.generate(model, observer);
			// Check
			assertEquals(report, unitUnderTest.generate(model, observer));
		}

		@Test
		void callsTheFileSystemPortCorrectly() {
			// Prepare
			when(generatorConfiguration.getGeneratorType()).thenReturn(GeneratorType.CLASS);
			when(model.getClasses()).thenReturn(Map.of(CLASS_NAME, classModel));
			when(model.getGeneratorConfigurations()).thenReturn(List.of(generatorConfiguration));
			when(reportFactory.create()).thenReturn(report);
			when(templateProcessingPort.process(generatorConfiguration, classModel)).thenReturn(CLASS_CODE);
			// Run
			unitUnderTest.generate(model, observer);
			// Check
			verify(observer, times(1)).classCodeGenerated(CLASS_CODE);
		}
	}
}
