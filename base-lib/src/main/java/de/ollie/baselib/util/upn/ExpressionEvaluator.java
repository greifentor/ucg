package de.ollie.baselib.util.upn;

import de.ollie.baselib.util.upn.impl.model.Expression;
import java.util.Stack;

public interface ExpressionEvaluator {
	Stack<Object> evaluate(Expression expression);
}
