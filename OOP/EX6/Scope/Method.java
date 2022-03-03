package oop.ex6.Scope;

import oop.ex6.Variable.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * the Method in the file are saved and create
 */
public class Method extends oop.ex6.Scope.Scope {
	 /* the parameters we read in the declaration line */
	private final ArrayList<Variable> parameters;
	/* the name of the method */
	private final String methodName;

	/**
	 * Constructor of the method class
	 * @param name the name of this method
	 * @param globalScope the global scope
	 * @param parameters the parameters in the declaration line
	 */
	public Method(String name, Scope globalScope, ArrayList<Variable> parameters) {
		super(globalScope);
		this.parameters = parameters;
		this.methodName = name;
		/* Register the parameters as local variables */
		for (Variable param: parameters) {
			this.localVariables.put(param.getName(), param);
		}
	}

	/**
	 * @return the name of this method
	 */
	public String getName() {
		return methodName;
	}

	/**
	 *  Checks if the parameters are legal in a call to a method.
	 * @param parameters the parameters from the call.
	 * @return true if the parameters are legal, false otherwise.
	 */
	public boolean checkParameters(ArrayList<String> parameters) {
		if (parameters.size() != this.parameters.size()) return false;
		for (int i = 0; i < parameters.size(); i++) {
			if (!this.isParameterLegal(i, parameters.get(i))) return false;
		}
		return true;
	}

	/**
	 * A helper method to checkParameters - checks if a single parameter is legal
	 * @param index The index of the parameter in the list.
	 * @param assignment The assignment to heck.
	 * @return true if legal, false otherwise.
	 */
	private boolean isParameterLegal(int index, String assignment) {
		if (index > this.parameters.size()) return false;
		Variable param = this.parameters.get(index);
		Type type = param.getType();
		return type.accept(assignment);
	}

}
