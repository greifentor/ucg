package de.ollie.ucg.core.service.impl;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.service.GeneratorExpressionEvaluationService;
import de.ollie.ucg.core.service.port.UpnEvaluationPort;
import jakarta.inject.Named;
import java.util.Stack;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class GeneratorExpressionEvaluationServiceImpl implements GeneratorExpressionEvaluationService {

	private final UpnEvaluationPort upnEvaluationPort;

	@Override
	public boolean suppressGeneratorForClassModel(ClassModel classModel, GeneratorSetting generatorSetting) {
		ensure(classModel != null, "class model cannot be null!");
		ensure(generatorSetting != null, "generator setting cannot be null!");
		if (generatorSetting.getGenerateWhen() != null) {
			Stack<Object> stack = upnEvaluationPort.evaluate(generatorSetting.getGenerateWhen(), classModel);
			return !stack.isEmpty() && !Boolean.TRUE.equals(stack.pop());
		}
		return false;
	}
}
