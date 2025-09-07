package de.ollie.ucg.core.service.port;

import de.ollie.ucg.core.model.ClassModel;
import java.util.Stack;

public interface UpnEvaluationPort {
	Stack<Object> evaluate(String expression, ClassModel classModel);
}
