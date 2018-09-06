package org.opennars.middle.operatorreflection;

import org.opennars.entity.Task;
import org.opennars.interfaces.Pluggable;
import org.opennars.interfaces.Timable;
import org.opennars.language.Term;
import org.opennars.operator.Operation;
import org.opennars.operator.Operator;
import org.opennars.storage.Memory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the exposed methods which are callable by Operators from NARS
 */
public class OperatorReflection {
    public Map<String, MethodInvocationOperator> methodOperators = new HashMap<>();

    /**
     * adds and registers a method without arguments
     *
     * @param pluggable used to register the operator
     * @param narsOperatorName name of the operator as called by NARS
     * @param object object of method call
     * @param methodName name of the called method
     * @param argumentTypes the types of the called method
     */
    public void appendAndRegisterMethodWithoutArguments(final Pluggable pluggable, final String narsOperatorName, final Object object, final String methodName, final List<Class> argumentTypes) throws NoSuchMethodException {
        Method calledMethod = retMethodByName(object, methodName);

        MethodInvocationOperator operator = new MethodInvocationOperator(narsOperatorName, object, calledMethod, argumentTypes);

        // register operator
        pluggable.addPlugin(operator);

        methodOperators.put(narsOperatorName, operator);
    }

    protected Method retMethodByName(final Object object, final String methodName) throws NoSuchMethodException {
        return object.getClass().getMethod(methodName);
    }
}
