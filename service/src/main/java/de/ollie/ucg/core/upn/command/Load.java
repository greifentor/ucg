package de.ollie.ucg.core.upn.command;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.baselib.util.upn.exception.MissingArgumentExpressionEvaluationException;
import de.ollie.baselib.util.upn.impl.model.Command;
import java.util.Map;
import java.util.Stack;

public class Load extends Command {

	public static final String TOKEN = "Load";

	public Load() {
		super(TOKEN);
	}

	@Override
	public Stack<Object> evaluate(Stack<Object> stack, Map<String, Object> valueStore) {
		ensure(stack.size() > 0, new MissingArgumentExpressionEvaluationException(1, this));
		String propertyName = (String) stack.pop();
		stack.push(valueStore.get(propertyName));
		return stack;
	}
}
