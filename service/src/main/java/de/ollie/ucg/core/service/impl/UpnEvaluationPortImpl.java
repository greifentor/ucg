package de.ollie.ucg.core.service.impl;

import de.ollie.baselib.util.upn.ExpressionEvaluator;
import de.ollie.baselib.util.upn.StringToExpressionConverter;
import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.service.port.UpnEvaluationPort;
import jakarta.inject.Named;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class UpnEvaluationPortImpl implements UpnEvaluationPort {

	private final ExpressionEvaluator expressionEvaluator;
	private final StringToExpressionConverter stringToExpressionConverter;

	@Override
	public Stack<Object> evaluate(String expression, ClassModel classModel) {
		Map<String, Object> valueStore = new HashMap<>();
		valueStore.put("classModel", classModel);
		return expressionEvaluator.evaluate(stringToExpressionConverter.convert(expression), valueStore);
	}
}
