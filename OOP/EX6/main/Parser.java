package oop.ex6.main;

import oop.ex6.Scope.*;
import oop.ex6.Variable.*;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class parse a s-Java file and checks if it's valid. Throw an exception if failed in any point.
 */
public class Parser {

	/* ********************** Data members ********************** */
	/* The global scope object. */
	private  final Scope globalScope = ScopeFactory.getGlobalScope();

	/* The Method object of the methods declared in the global scope. */
	private  final ArrayList<Method> methods = new ArrayList<>();

	/* A hash map that maps methods name to the Methods instances, for efficient retrieval */
	private  final HashMap<String, Method> methodsNames = new HashMap<>();

	/* Each sub ArrayList is a single method's content. */
	private  final ArrayList<ArrayList<String>> rawMethods = new ArrayList<>();

	private  BufferedReader br;

	/* ********************** Main Methods ********************** */

	/**
	 * Parse the global scope
	 * @param file The s-Java file object to parse.
	 * @throws IOException If There is a problem with the buffer reader or the file object.
	 * @throws VariableException For some problem in creating a variable.
	 * @throws LocalScopeException For some problem in adding global variables to the global scope.
	 * @throws GlobalScopeException For some problem in the structure of the file content.
	 */
	public  void parseGlobalScope(File file)
			throws IOException, VariableException, LocalScopeException, GlobalScopeException {
		try {
			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			while (line != null) {
				if (line.matches(RegexClass.COMMENT_LINE) || line.matches(RegexClass.POSSIBLE_WHITESPACES)) {
					line = br.readLine();
					continue;
				} else if (line.matches(RegexClass.VARIABLE)) {
					Map<String, Variable> newVars = VariablesFactory.createVariablesInLine(line,
																						   globalScope);
					globalScope.addVariables(newVars);
					line = br.readLine();
					continue;
				} else if (line.matches(RegexClass.METHOD_DECLARATION)) {
					ArrayList<String> newRawMethod = documentMethod(line);
					rawMethods.add(newRawMethod);
					line = br.readLine();
					continue;
				}
				throw new GlobalScopeException();
			}
		} finally {
			br.close();
		}
	}

	/**
	 * Document the Method's scope content for later parsing.
	 * @param declarationLine The first line of the method, containing it's name and it's expected parameters.
	 * @return ArrayList with the lines of the method, by order.
	 * @throws GlobalScopeException If there is a problem with the structure of the global scope.
	 * @throws IOException If there is a problem with the buffer reader.
	 */
	 private ArrayList<String> documentMethod(String declarationLine)
			throws GlobalScopeException, IOException {
		ArrayList<String> rawMethod = new ArrayList<>();
		rawMethod.add(declarationLine);
		String line = br.readLine();
		int innerScopeCounter = 1; // Count the inner scopes of this method.
		while (true) {
			if (line == null) break;
			rawMethod.add(line);
			if (line.matches(RegexClass.END_SCOPE_LINE)) innerScopeCounter--;
			if (line.matches(RegexClass.CONDITION_DECLARATION)) innerScopeCounter++;
			if (innerScopeCounter == 0) break;
			line = br.readLine();
		}
		if (innerScopeCounter > 0) throw new GlobalScopeException();
		return rawMethod;
	}

	/**
	 * Parse all the methods by the order of declaration.
	 */
	 public void parseAllMethods() throws GlobalScopeException, LocalScopeException, VariableException {
		this.initializeMethods();
		for (int i = 0; i < rawMethods.size(); i++) {
			ArrayList<String> rawMethod = rawMethods.get(i);
			Method method = methods.get(i); // Both arrays will always have the same size.
			if (!parseSingleMethod(rawMethod, method)) throw new GlobalScopeException();
		}
	}

	/**
	 * Initialize the method instances and put them in the methods array by order of declaration.
	 * @throws GlobalScopeException If there is a problem in creating the Method instance.
	 */
	 private void initializeMethods() throws GlobalScopeException {
		for (ArrayList<String> rawMethod: rawMethods) {
			Method newMethod = ScopeFactory.createMethodScope(rawMethod.get(0), globalScope);
			/* The next line checks for methods duplication. */
			if (methodsNames.containsKey(newMethod.getName())) throw new GlobalScopeException();
			methodsNames.put(newMethod.getName(), newMethod);
			methods.add(newMethod);
		}
	}

	/**
	 * Parse a single method.
	 * @param rawMethod An arrayList with the lines of the method content, by order.
	 * @param method The method object.
	 * @return true if the method is written legally, false otherwise.
	 */
	 private boolean parseSingleMethod(ArrayList<String> rawMethod, Scope method)
			throws LocalScopeException, VariableException, GlobalScopeException {
		LinkedList<Scope> scopes = new LinkedList<>();
		Scope currentScope = method;
		for (int i = 1; i < rawMethod.size() - 2; i++) {
			String line = rawMethod.get(i);
			if (line.matches(RegexClass.COMMENT_LINE) || line.matches(RegexClass.POSSIBLE_WHITESPACES) ||
				  line.matches(RegexClass.RETURN_STATEMENT)) continue;
			if (line.matches(RegexClass.CONDITION_DECLARATION)) {
				scopes.addFirst(currentScope);
				currentScope = ScopeFactory.createConditionScope(line, currentScope);
				continue;
			}
			if (line.matches(RegexClass.END_SCOPE_LINE)) {
				currentScope = scopes.poll();
				if (currentScope == null) return false;
				continue;
			}
			if (line.matches(RegexClass.METHOD_CALL)) {
				if (!isMethodCallLegal(line)) return false;
				continue;
			}
			if (line.matches(RegexClass.VARIABLE)) {
				Map<String, Variable> variablesInLine = VariablesFactory.createVariablesInLine(line, currentScope);
				currentScope.addVariables(variablesInLine);
			}
		}

		/* Check the last 2 lines that have to be in a single format. */
		if (!rawMethod.get(rawMethod.size() - 2).matches(RegexClass.RETURN_STATEMENT)) return false;
		if (!rawMethod.get(rawMethod.size() - 1).matches(RegexClass.END_SCOPE_LINE)) return false;
		if (currentScope != method) throw new GlobalScopeException();
		return true;
	}

	/* ********************** Helper Methods ********************** */
	/**
	 * Checks if a method call is legal (if such method does exists and if the parameters are legal).
	 * @param line The line of the method call.
	 * @return true if the call is legal, false otherwise.
	 */
	 private boolean isMethodCallLegal(String line) {
		ArrayList<String> methodArguments = new ArrayList<>();
		Map<String,Method> methodMap = new HashMap<>();
		for (Method m: this.methodsNames.values() ) {
			methodMap.put(m.getName(),m);

		}
		Pattern p = Pattern.compile(RegexClass.VAR_NAME); // Catches the method name as well
		Matcher m = p.matcher(line);
		while (m.find()) {
			methodArguments.add(line.substring(m.start(), m.end()));
		}

		/*  Check if the methods name exists */
		String methodName = methodArguments.get(0);
		boolean b = methodMap.containsKey(methodName);
		if (!b) return false;

		/* Check if the parameters are legal */
		Method method = methodMap.get(methodName);
		ArrayList<String> parameters = (ArrayList<String>) methodArguments.subList(1, methodArguments.size());
		return method.checkParameters(parameters);
	}
}
