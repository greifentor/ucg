package de.ollie.baselib.util.upn.impl;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.baselib.util.upn.ExpressionEvaluator;
import de.ollie.baselib.util.upn.impl.model.Expression;
import jakarta.inject.Named;
import java.util.Map;
import java.util.Stack;

@Named
public class ExpressionEvaluatorImpl implements ExpressionEvaluator {

	public static final String MESSAGE_CONTAINS_CONTAINED_ARGUMENT_MISSED =
		"missing argument to check for being contained";
	public static final String MESSAGE_CONTAINS_CONTAINING_ARGUMENT_MISSED =
		"missing argument to be checked for containing another element";

	@Override
	public Stack<Object> evaluate(Expression expression, Map<String, Object> valueStore) {
		ensure(expression != null, "expression cannot be null!");
		ensure(valueStore != null, "value store cannot be null!");
		Stack<Object> stack = new Stack<>();
		expression
			.getWords()
			.forEach(word -> {
				word.evaluate(stack, valueStore);
			});
		return stack;
	}
}
