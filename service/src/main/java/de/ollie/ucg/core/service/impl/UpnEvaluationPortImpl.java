package de.ollie.ucg.core.service.impl;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.baselib.util.upn.ExpressionEvaluator;
import de.ollie.baselib.util.upn.StringToExpressionConverter;
import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.service.port.UpnEvaluationPort;
import jakarta.inject.Named;
import java.util.Map;
import java.util.Stack;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class UpnEvaluationPortImpl implements UpnEvaluationPort {

	private final ExpressionEvaluator expressionEvaluator;
	private final HashMapFactory hashMapFactory;
	private final StringToExpressionConverter stringToExpressionConverter;

	@Override
	public Stack<Object> evaluate(String expression, ClassModel classModel) {
		ensure(classModel != null, "class model cannot be null!");
		ensure(expression != null, "expression cannot be null!");
		Map<String, Object> valueStore = hashMapFactory.createMapStringObject();
		valueStore.put("classModel", classModel);
		return expressionEvaluator.evaluate(stringToExpressionConverter.convert(expression), valueStore);
	}
}
