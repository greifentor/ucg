package de.ollie.ucg.core.service;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.GeneratorSetting;

public interface GeneratorExpressionEvaluationService {
	boolean suppressGeneratorForClassModel(ClassModel classModel, GeneratorSetting generatorSetting);
}
