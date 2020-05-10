package com.wuda.tester.mysql.commons.keygen;

/**
 * 时间被向后调整异常.
 *
 * @author wuda
 * @version 1.0
 */
public class KenGenTimeBackwardsException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message
     *         the detail message. The detail message is saved for
     *         later retrieval by the {@link #getMessage()} method.
     */
    public KenGenTimeBackwardsException(String message) {
        super(message);
    }
}
