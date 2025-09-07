package de.ollie.ucg.core.upn.command;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.baselib.util.upn.exception.MissingArgumentExpressionEvaluationException;
import de.ollie.baselib.util.upn.exception.WrongArgumentTypeExpressionEvaluationException;
import de.ollie.baselib.util.upn.impl.model.Command;
import de.ollie.ucg.core.model.PropertyOwner;
import java.util.Map;
import java.util.Stack;

public class HasProperty extends Command {

	public static final String TOKEN = "HasProperty";

	public HasProperty() {
		super(TOKEN);
	}

	@Override
	public Stack<Object> evaluate(Stack<Object> stack, Map<String, Object> valueStore) {
		ensure(stack.size() > 1, new MissingArgumentExpressionEvaluationException(2, this));
		ensure(
			stack.peek() instanceof String,
			new WrongArgumentTypeExpressionEvaluationException(1, this, String.class, stack.peek().getClass())
		);
		String propertyName = (String) stack.pop();
		ensure(
			stack.peek() instanceof PropertyOwner,
			new WrongArgumentTypeExpressionEvaluationException(1, this, PropertyOwner.class, stack.peek().getClass())
		);
		stack.push(((PropertyOwner) stack.pop()).hasPropertyWithName(propertyName));
		return stack;
	}
}
