package de.ollie.ucg.core.service.impl;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.service.GeneratorExpressionEvaluationService;
import de.ollie.ucg.core.service.port.UpnEvaluationPort;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class GeneratorExpressionEvaluationServiceImpl implements GeneratorExpressionEvaluationService {

	private final UpnEvaluationPort upnEvaluationPort;

	@Override
	public boolean suppressGeneratorForClassModel(ClassModel classModel, GeneratorSetting generatorSetting) {
		ensure(classModel != null, "class model cannot be null!");
		ensure(generatorSetting != null, "generator setting cannot be null!");
		// Stack<Object> stack = new Stack<>();
		// stack.push(classModel);
		//
		// stack = upnEvaluationPort.evaluate(stack,
		// generatorSetting.getGenerateWhen());
		if (generatorSetting.getGenerateWhen() != null) {
			return false;
		}
		return false;
	}
}
