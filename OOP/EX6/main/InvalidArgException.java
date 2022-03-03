package oop.ex6.main;

import java.io.IOException;

/**
 * A type 2 error - when the arguments are invalid we throw this exception
 */
public class InvalidArgException extends IOException {
    private final static long serialVersionUID = 1L;

    /**
     *  create new exception with given message.
     * @param message An informative message to the user about the problem.
     */
    public InvalidArgException(String message){
        super(message);
    }
}
