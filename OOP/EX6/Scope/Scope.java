package oop.ex6.Scope;

import oop.ex6.Variable.*;


import java.util.HashMap;
import java.util.Map;

public class Scope {
	private final Map<String, Variable> outerScopeVariables;
	protected final Map<String, Variable> localVariables;

	/* ********************** Constructors ********************** */
	public Scope(Map<String, Variable> outerScopeVars) {
		this.outerScopeVariables = outerScopeVars;
		this.localVariables = new HashMap<>();
	}

	/**
	 * An inner scope (condition scope) constructor - create the scope with given scope's variables as the
	 * outer scope variables.
	 * @param outerScope The outer scope of this new scope;
	 */
	public Scope(Scope outerScope) {
		this.outerScopeVariables = outerScope.getAllVariables();
		this.localVariables = new HashMap<>();
	}

	/* ********************** Methods ********************** */

	/**
	 * Add few variables to the scope.
	 * @param variableMap A map of variables that maps variable name's to Variable objects.
	 * @throws VariableException If some exception occurred in the variable level.
	 * @throws LocalScopeException If the variable is illegal in the scope level.
	 */
	public void addVariables(Map<String, Variable> variableMap) throws VariableException,
																	   LocalScopeException {
		for (String varName: variableMap.keySet()) {
			Variable variable = variableMap.get(varName);
			if (!this.checkVariable(variable)) throw new LocalScopeException();
			// If the variable is only assigned after declared in some line before.
			if (variable.getType() == null) variable = this.mergeVariables(varName, variable);
			this.localVariables.put(varName, variable);
		}
	}

	/**
	 * Add a new variable to the scope.
	 * @param newVar The new variable to add.
	 */
	private boolean checkVariable(Variable newVar) {
		/* In the case that there is already a variable with this name in the local scope */
		if (this.localVariables.containsKey(newVar.getName())) {
			Variable localVar = this.localVariables.get(newVar.getName());
			if (localVar.isFinal()) return false; // Because a final variable cannot be assigned.
			if (newVar.getType() != null) return false; // Two local var's with the same name in one scope
			return localVar.getType().accept(newVar.getValue()); // Returns false for illegal assignment.

			/* In the case that there is already a variable with this name in the global scope */
		} else if (this.outerScopeVariables.containsKey(newVar.getName())) {
			Variable globalVar = this.outerScopeVariables.get(newVar.getName());
			// The next line return false for illegal assignment.
			if (newVar.getType() == null) return globalVar.getType().accept(newVar.getValue());
			return true;
		}
		/* If this variable had never been declared in any relevant scope  */
		return newVar.getType() != null;
	}

	/** todo not so much elegant method.
	 * Merge a declaration variable with an assignment variable.
	 * @param varName The name of the new variable.
	 * @param variable The new variable.
	 * @return A merged new variable with the proper type and value.
	 */
	private Variable mergeVariables(String varName, Variable variable) throws VariableException {
		Variable withType = variable; // Set as default to prevent NullPointerException.
		if (this.localVariables.containsKey(varName)) withType = this.localVariables.get(varName);
		else if (this.outerScopeVariables.containsKey(varName)) withType = this.outerScopeVariables.get(varName);
		withType.setValue(variable.getValue());
		return withType;
	}

	/**
	 * Return whether or not a variable is exists in the scope, and can function as a boolean.
	 * @param varName The variable name.
	 * @return true if a variable with that name exists in this scope and has a boolean value and it is
	 * initialized, false otherwise.
	 */
	public boolean isBoolean(String varName) {
		Variable var = null;
		if (this.outerScopeVariables.containsKey(varName)) var = this.outerScopeVariables.get(varName);
		if (this.localVariables.containsKey(varName)) var = this.localVariables.get(varName);
		if (var != null) {
			Type varType = var.getType();
			return ((varType == Type.BOOLEAN || varType == Type.INTEGER || varType == Type.DOUBLE) &&
					var.isInitialized());
		}
		return false;
	}

	/**
	 * Return the variable with this given name from this scope.
	 * @param varName The variable's name.
	 * @return A variable object if a variable with this name exists in the scope, null if it doesn't exist.
	 */
	public Variable getVariable(String varName) {
		if (this.localVariables.containsKey(varName)) return this.localVariables.get(varName);
		if (this.outerScopeVariables.containsKey(varName)) return this.outerScopeVariables.get(varName);
		return null;
	}

	/**
	 * Returns true if, and only if, a variable with a given name was created in this scope.
	 * @param varName The variable name to check.
	 * @return true if a variable with the given varName was created in this scope, false otherwise.
	 */
	public boolean isLocal(String varName) { // todo needed?
		return this.localVariables.containsKey(varName);
	}

	/**
	 * Return all of the variables in this scope in a united map, in which the "last word" goes for the
	 * current scope variables (a helper method for creating a nested scope).
	 * @return A map that maps Strings to Variable instances.
	 */
	private Map<String, Variable> getAllVariables() {
		Map<String, Variable> allVariables = this.outerScopeVariables;
		for (String varName: this.localVariables.keySet()) {
			allVariables.put(varName, this.localVariables.get(varName));
		}
		return allVariables;
	}
}
