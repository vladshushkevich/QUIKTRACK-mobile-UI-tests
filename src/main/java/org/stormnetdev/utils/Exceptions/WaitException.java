package org.stormnetdev.utils.Exceptions;

/**
 * Created by baranovski on 12/5/17.
 */
public class WaitException extends RuntimeException {

    /**
     * Constructs a {@code MyException} with no detail message.
     */
    private WaitException() {
        super();
    }

    /**
     * Constructs a {@code MyException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public WaitException(String s) {
        super(s);
    }

}
