package oop.ex6.Scope;

/**
 * A local scope-level related exception, that occurs in a well defined scope in the matters of variable
 * collisions.
 */
public class LocalScopeException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new ScopeException.
	 */
	public LocalScopeException() {
		super();
	}
}
