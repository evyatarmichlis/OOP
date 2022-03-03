package oop.ex6.Variable;

import oop.ex6.Scope.LocalScopeException;
import oop.ex6.Scope.Scope;
import oop.ex6.main.Constants;
import oop.ex6.main.RegexClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  make variables from the lines  in the file
 */
public class VariablesFactory {

    /**
     * extract the vars from the method declaration
     *
     * @param line the line of the method declaration
     */
    public static ArrayList<Variable> createParameters(String line) {
        /* check if the line is in the right syntax */
        ArrayList<Variable> variablesList = new ArrayList<>();

        /* make a list of parameters from the line */
        String[] parameters = line.split(Constants.COMMA);

        /* use loop to make one variable for each declared parameter */
        for (String param : parameters) {
            boolean isFinal = false;
            Type type;
            String name;
            if (param.contains(Constants.FINAL)) {
                isFinal = true;
                param = param.replaceAll(RegexClass.POSSIBLE_WHITESPACES +
                                         Constants.FINAL + RegexClass.POSSIBLE_WHITESPACES, ""); }
            param = param.trim();
            String[] typeAndName = param.split(RegexClass.FEW_SPACES);
            type = Type.getEnum(typeAndName[0]);
            name = typeAndName[1];
            Variable v = new Variable(isFinal, type, name, true, null);
            variablesList.add(v);}
        return variablesList;
    }

    /**
     * read a line of vars in the file and extract the vars from it
     * @param line line of strings in the file
     * @return A hash map whose that maps variable names to variable objects.
     * @throws VariableException if the line is invalid
     */
    public static Map<String, Variable> createVariablesInLine(String line, Scope currentScope)
            throws VariableException, LocalScopeException {
        /* Initiate the default modifiers and the hashMap that will contain the values */
        boolean isFinal = false;
        Type type = null;

        /* Find the variables' type and whether or not they are final. */
        Pattern pattern = Pattern.compile(RegexClass.VARIABLE);
        Matcher m = pattern.matcher(line);

        if (m.find()) {
            if (m.group(1) != null) {
                isFinal = true;
                line = line.replace(Constants.FINAL, "");
            }
            if (m.group(2) != null) {
                type = Type.getEnum(m.group(2).trim());
                line = line.replace(type.getReserveWord(), "");
            }
        }

        /* Split the line to arguments (without "final" and the variable's type) */
        String[] arguments = line.trim().replace(" ", "").split(Constants.COMMA);

        Map<String, Variable> variablesInLine = new HashMap<>();
        /* Create the Variable objects and add them to the HashMap */
        for (String varNameAndValue : arguments) {
            Variable newVar = createSingleVariable(varNameAndValue, isFinal, type, currentScope);
            /* Checks if there is two variables in this line with the same name. */
            if (variablesInLine.containsKey(newVar.getName())) throw new LocalScopeException();
            variablesInLine.put(newVar.getName(),newVar);

        }
        /* Returns the proper hash map using the helper function */
        return variablesInLine;
    }

    /**
     * Gets an array of arguments represents variables in a single s-Java line, their type and their 'final'
     * status, and insert appropriate Variable instances into a hashmap that maps the variable names to the
     * Variable objects.
     * @param isFinal The "final" status of all the variables.
     * @param type The type of all the variables.
     * @param currentScope The current scope of the variables (for a case of assignment of an existed
     * variable).
     * @return A hash map that maps variables' names (Strings) to their Variable objects.
     * @throws VariableException If the line is invalid.
     */
    private static Variable createSingleVariable(String varNameAndValue, boolean isFinal, Type type,
                                                 Scope currentScope) throws VariableException {
            String name;
            String val = null;
            if (varNameAndValue.contains(Constants.ASSIGNMENT)) {
                String[] strings = varNameAndValue.split(Constants.ASSIGNMENT);
                name = strings[0];
                val = strings[1].replaceAll(RegexClass.END_LINE,"");
            } else {
                /* In the case of a final variable is declared but isn't assigned */
                if (isFinal) throw new VariableException();
                name = varNameAndValue;
            }

            /* Checks for the case of assignment of a reference to another variable */
            if (val != null && val.matches(RegexClass.VAR_NAME)) { // todo maybe more simple?
                Variable assignedVariable = currentScope.getVariable(val);
                if ((assignedVariable == null || !assignedVariable.isInitialized()) &&
                    !val.equals(Constants.TRUE) && !val.equals(Constants.FALSE)) throw new VariableException();
                if (assignedVariable != null) val = assignedVariable.getValue();
            }
        return new Variable(isFinal, type, name.replaceAll(RegexClass.END_LINE, ""), val);
    }
}
