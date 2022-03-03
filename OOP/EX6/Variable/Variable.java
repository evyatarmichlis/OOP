package oop.ex6.Variable;

/**
 * This class represents a variable in s-Java.
 */
public class Variable {


	private final String name;
	private final boolean isFinal;
	private boolean isInitialized;
	private final Type type;
	private String value;

	/* ******************** Constructors ******************** */

	/**
	 * A regular constructor - when creating a new Variable in any scope.
	 * @param isFinal A boolean indicates whether or not this variable declared as final.
	 * @param type The type of this variable.
	 * @param name The name of this variable.
	 * @param value The value of this variable.
	 * @throws VariableException If the value doesn't match the type.
	 */
	public Variable(boolean isFinal, Type type, String name, String value) throws VariableException {
		if (type != null && !type.accept(value)) {
			throw new VariableException();
		}
		this.isFinal = isFinal;
		this.type = type;
		this.name = name;
		this.isInitialized = (value != null);
		this.value = value;
	}

	/**
	 * A parameter constructor - when needed to declare in specific that this variable was initialized
	 * without necessarily giving it a non null value.
	 */
	public Variable(boolean isFinal, Type type, String name, boolean isInitialized, String value) {
		this.isFinal = isFinal;
		this.type = type;
		this.name = name;
		this.isInitialized = isInitialized;
		this.value = value;
	}

	/* ******************** Getters ******************** */

	/**
	 * @return The variable's name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return The variable's type.
	 */
	public Type getType() {
		return this.type;
	}

	/**
	 * @return The variable's value (in a String format).
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * @return True if this variable declared final, false otherwise.
	 */
	public boolean isFinal() {
		return this.isFinal;
	}

	/**
	 * @return True if the variable initialized and false if it was declared only.
	 */
	public boolean isInitialized() {
		return this.isInitialized;
	}


	/* ******************** Setters ******************** */

	/**
	 * Set the value of this variable.
	 * @param newValue The new value to set.
	 * @throws VariableException If the variable is final and therefore cannot be assigned, or if the
	 * variable's type doesn't match the new value.
	 */
	public void setValue(String newValue) throws VariableException {
		if (this.isFinal || !this.type.accept(newValue)) throw new VariableException();
		this.value = newValue;
		this.isInitialized = true;
	}
}
