package org.opennars.middle.operatorreflection;

import org.opennars.entity.Task;
import org.opennars.interfaces.Pluggable;
import org.opennars.interfaces.Timable;
import org.opennars.language.Term;
import org.opennars.operator.Operation;
import org.opennars.operator.Operator;
import org.opennars.storage.Memory;

/**
 * Exposes a method of a object as a callable operator
 */
	public class MethodInvocationOperator extends Operator {
	private Method method;
	private Object object;

	/**
	 * @param narsOperatorName name of the operator as called by NARS
	 * @param object object of method call
	 * @param method called method
	 */
	public MethodInvocationOperator(String narsOperatorName, Object object, Method method) {
	    super(narsOperatorName);

	    this.object = object;
	    this.method = method;
	}

	@Override
	protected List<Task> execute(Operation operation, Term[] args, Memory memory, Timable time) {
	    try {
		method.invoke(object);
	    }
	    catch (IllegalAccessException e) {
		// ignore
	    }
	    catch (InvocationTargetException e) {
		// ignore
	    }

	    return null;
	}
}
