package oop.ex6.Variable;

import oop.ex6.main.Constants;
import oop.ex6.main.RegexClass;
import java.util.regex.Pattern;

/**
 * This enum represents the different types of variables.
 * Each type also has a regex pattern that represents it.
 */
public enum Type {
    INTEGER(RegexClass.INT, Constants.INT),
    DOUBLE(RegexClass.DOUBLE, Constants.DOUBLE),
    CHAR(RegexClass.CHAR, Constants.CHAR),
    STRING(RegexClass.STRING, Constants.STRING),
    BOOLEAN(RegexClass.BOOLEAN, Constants.BOOLEAN);

    /* ******************** Data Members ******************** */
    /* The pattern object compiled with the proper regex of values of that type. */
    private final Pattern pattern;

    /* The reserved word in s-Java for that type. */
    private final String reserveWord;


    /* ******************** Constructor ******************** */
    Type(String regex, String reserveWord) {
        this.pattern = Pattern.compile(regex);
        this.reserveWord = reserveWord;
    }

    /* ******************** Methods ******************** */
    /**
     * Get the proper type from it's reserve word.
     * @param type The reserve word for this type.
     * @return An enum that matches this reserve word.
     */
    public static Type getEnum(String type) {
        Type t;
        switch (type) {
            case Constants.INT:
                t = Type.INTEGER;
                break;
            case Constants.DOUBLE:
                t = Type.DOUBLE;
                break;
            case Constants.BOOLEAN:
                t = Type.BOOLEAN;
                break;
            case Constants.CHAR:
                t = Type.CHAR;
                break;
            case Constants.STRING:
                t = Type.STRING;
                break;
            default: t = null;
        };
        return t;
    }

    /**
     * @param value the value to match.
     * @return true if the string matches the Type regex pattern, and false otherwise.
     */
    public boolean accept(String value) {
        if (value == null) return true;
        return this.pattern.matcher(value).matches();
    }

    /**
     * @return The reserve word for this enum.
     */
    public String getReserveWord() {
        return this.reserveWord;
    }
}

