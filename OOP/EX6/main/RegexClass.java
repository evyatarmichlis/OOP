package oop.ex6.main;

import oop.ex6.Variable.Type;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** the class of all of the regex we need in the program **/
public class RegexClass {
	/* ****************** Types ****************** */
	public static final String INT = "-?\\d+";
	public static final String DOUBLE = "-?\\d*.?\\d+";
	public static final String CHAR = "'.'";
	public static final String STRING = "\".*\"";
	public static final String BOOLEAN =
			Constants.TRUE + "|" + Constants.FALSE + "|" + DOUBLE;

	/* ****************** common use regex ****************** */
	public static final String POSSIBLE_WHITESPACES = "\\s*";
	public static final String FEW_SPACES = Constants.WHITE_SPACE + "+";
	public static final String COMMENT_LINE = "//.*";
	public static final String OR = "[|]{2}";
	public static final String AND = "&&";
	public static final String END_LINE = POSSIBLE_WHITESPACES + Constants.END_LINE + POSSIBLE_WHITESPACES;
	public static final String RETURN_STATEMENT = POSSIBLE_WHITESPACES +Constants.RETURN +
			POSSIBLE_WHITESPACES + Constants.END_LINE;
	public static final String END_SCOPE_LINE = POSSIBLE_WHITESPACES + Constants.END_SCOPE +
			POSSIBLE_WHITESPACES;
	public static final String METHOD_NAME = "[a-zA-Z]" + "\\w*";
	/** the parts of the lines in the file**/
	/* the possibles names for vars */
	public static final String VAR_NAME = "(("+ "[a-zA-Z]" + "\\w*"
			+ POSSIBLE_WHITESPACES + ")" + "|" + "("+
			Constants.UNDERSCORE + "[a-zA-Z0-9_]+"+ POSSIBLE_WHITESPACES +"))" ;

	/* condition can be another var */
	public static final String CONDITION =  BOOLEAN+ "|" + VAR_NAME;

	/* the type of the vars */
	public  static  final  String TYPE = "(" +Constants.STRING + "|" + Constants.INT +
			"|" + Constants.BOOLEAN +
			"|" + Constants.DOUBLE + "|" + Constants.CHAR +")" ;
	/* the value of a var */
	public static final String VALUE = "(" + INT + "|" + CHAR + "|" + STRING
			+ "|" + BOOLEAN + ")" ;

	/* ****************** Complected Regex's ****************** */
	/* check if the line is  a new method declaration  */
	public static final String METHOD_DECLARATION = POSSIBLE_WHITESPACES
			+ Constants.VOID + FEW_SPACES + METHOD_NAME +
			POSSIBLE_WHITESPACES
			+"\\((("+POSSIBLE_WHITESPACES +Constants.FINAL + FEW_SPACES + ")?" +
			 TYPE  + FEW_SPACES
			 + VAR_NAME +POSSIBLE_WHITESPACES+
			"("+Constants.COMMA + POSSIBLE_WHITESPACES +
			"("+Constants.FINAL + FEW_SPACES + ")?"
			+ "("+ TYPE + ")" + FEW_SPACES
			+ "(" + VAR_NAME+POSSIBLE_WHITESPACES+
			"))*)?\\)"+POSSIBLE_WHITESPACES + Constants.BEGIN_SCOPE ;
	/* check if the line is  a  method call  */
	public static final String METHOD_CALL = POSSIBLE_WHITESPACES
			+ METHOD_NAME + POSSIBLE_WHITESPACES + "\\((" +
			POSSIBLE_WHITESPACES + VAR_NAME
			+ POSSIBLE_WHITESPACES  +
			"("+Constants.COMMA +POSSIBLE_WHITESPACES +
			VAR_NAME + POSSIBLE_WHITESPACES + ")*)?"+
			"\\)" + POSSIBLE_WHITESPACES + END_LINE;

	/* check if the line is  a new condition declaration  */
	public static final String CONDITION_DECLARATION = POSSIBLE_WHITESPACES + "(" +
			Constants.IF + "|" + Constants.WHILE + ")" +
			POSSIBLE_WHITESPACES +
			"\\((" + POSSIBLE_WHITESPACES +
			CONDITION + "(" + OR + "|" +
			AND + ")" + POSSIBLE_WHITESPACES +
			")*" + POSSIBLE_WHITESPACES +
			"(" + CONDITION +
			POSSIBLE_WHITESPACES + "\\))"
			+ POSSIBLE_WHITESPACES + Constants.BEGIN_SCOPE + POSSIBLE_WHITESPACES;

	/* check if the line is variable line  */
	public static final String VARIABLE = ("(" +
			Constants.FINAL + FEW_SPACES + ")?"
			+ "((" + TYPE  +")"+ FEW_SPACES +")?"
			+ "(" + VAR_NAME + ")"
			+ POSSIBLE_WHITESPACES + "("
			+ Constants.ASSIGNMENT + POSSIBLE_WHITESPACES
			+ "(" + VALUE + "|" + VAR_NAME + POSSIBLE_WHITESPACES+ "))?"+ POSSIBLE_WHITESPACES + "(" +Constants.COMMA +
			POSSIBLE_WHITESPACES + VAR_NAME + POSSIBLE_WHITESPACES+
			"("+ Constants.ASSIGNMENT + POSSIBLE_WHITESPACES
			+ "((" + VALUE + "|" + VAR_NAME + POSSIBLE_WHITESPACES +")"+POSSIBLE_WHITESPACES+ ")"+ POSSIBLE_WHITESPACES + ")?)*"
			+ END_LINE);

	public static void main(String[] args) {
		System.out.println("System.out.println".matches(METHOD_NAME));

	}

}

