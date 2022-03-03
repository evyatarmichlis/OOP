package oop.ex6.Scope;

import oop.ex6.Variable.*;
import oop.ex6.main.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Create the scopes of our program:
 * when the parser read a line how start
 * a new scope we create new scope scope
 */
public class ScopeFactory {

    /** we create the global scope only once **/
//    private final static Scope globalScope = new Scope(new HashMap<String, Variable>());

    /**
     * Getter for the global scope instance.
     * @return A scope object of the global scope.
     */
    public static Scope getGlobalScope() {
        return new Scope(new HashMap<String, Variable>());
    }

    /**
     *  create new method
     * @param decLine the line of the method
     * @param globalScope the global scope
     * @return new method with the right variables
     */
    public static Method createMethodScope(String decLine, Scope globalScope)  {
        /* remove the "void" word */
        if (decLine.contains(Constants.VOID)) {
            decLine = decLine.replace(Constants.VOID, "");
        }

        /* Get the method name */
        String scopeName = decLine.substring(0, decLine.indexOf("("));

        /* Get the scope parameters */
        String scopeParameters = decLine.substring(decLine.indexOf("(") + 1, decLine.indexOf(")"));
        ArrayList<Variable> variables;
        if (scopeParameters.length() == 0) variables = new ArrayList<>();
        else variables = VariablesFactory.createParameters(decLine);

        /* Create the new method */
        return new Method(scopeName, globalScope, variables);
    }

    /**
     *  create a non method scope(if or while)
     * @param decLine the line we just read
     * @param outerScope the outer scope of the var
     * @return new scope of condition
     * @throws LocalScopeException if something goes wrong.
     */
    public static Scope createConditionScope(String decLine, Scope outerScope) throws LocalScopeException {
        /* spilt the line of the condition to the vars in it */
        String condition = decLine.substring(decLine.indexOf("(") + 1, decLine.indexOf(")"));
        condition = condition.replaceAll(Constants.IF + "|" + Constants.WHILE, "");
        condition = condition.replaceAll(RegexClass.OR + "|" + RegexClass.AND, " ");
        String[] conditionList = condition.split(RegexClass.FEW_SPACES);
        /* check if the vars in the line are match to boolean syntax */
        for (String s : conditionList) {
            if (!s.matches(RegexClass.BOOLEAN) && !outerScope.isBoolean(s)) throw new LocalScopeException();
        }
        return new Scope(outerScope);
    }
}
