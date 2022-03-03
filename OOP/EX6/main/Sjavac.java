package oop.ex6.main;

import oop.ex6.Scope.LocalScopeException;
import oop.ex6.Variable.VariableException;

import java.io.File;
import java.io.IOException;

public class Sjavac {

	/* ***************** Constants & Magic numbers ***************** */
	private static final int NUMBER_OF_ARGUMENTS = 1;
	private static final int FILE_ARG_INDEX = 0;
	private static final int LEGAL_CODE = 0;
	private static final int ILLEGAL_CODE = 1;
	private static final int IO_ERROR = 2;
	private static final String LEGAL_SUFFIX = ".sjava";

	/* ********************** Error messages ********************** */
	private static final String WRONG_NUMBER_OF_ARGUMENTS = "ERROR: Wrong number of arguments in the " +
															"command line";
	private static final String WRONG_SUFFIX = "ERROR: The file extension has to be 'sjava'.";
	private static final String FILE_NOT_FOUND = "ERROR: File not found.";

	public static void main(String[] args) {
		try {
			if (args.length != NUMBER_OF_ARGUMENTS) {
				throw new InvalidArgException(WRONG_NUMBER_OF_ARGUMENTS);
			}
			String filename = args[FILE_ARG_INDEX];
			if (!filename.endsWith(LEGAL_SUFFIX)) {
				throw new InvalidArgException(WRONG_SUFFIX);
			}
			File file = new File(filename);
			if (!file.exists()) {
				throw new InvalidArgException(FILE_NOT_FOUND);
			}
			Parser parser = new Parser();
			parser.parseGlobalScope(file);
			parser.parseAllMethods();
			System.out.println(LEGAL_CODE);
		} catch (GlobalScopeException | LocalScopeException | VariableException  e) {
			System.out.println(ILLEGAL_CODE);
		} catch (IOException e) {
			System.out.println(IO_ERROR);
			System.err.println(e.getMessage());
		}
	}
}
