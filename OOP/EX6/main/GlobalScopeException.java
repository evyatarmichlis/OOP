package oop.ex6.main;

/**
 * A global scope-level related exception, that occurs if the file format is illegal in the matters of
 * dividing to methods, opening illegal scopes in the global scope etc.
 */
public class GlobalScopeException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new ScopeException.
	 */
	public GlobalScopeException() {
		super();
	}
}
