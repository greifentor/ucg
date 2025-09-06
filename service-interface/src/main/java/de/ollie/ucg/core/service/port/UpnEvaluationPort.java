package de.ollie.ucg.core.service.port;

import java.util.Stack;

public interface UpnEvaluationPort {
	Stack<Object> evaluate(Stack<Object> stack, String expression);
}
