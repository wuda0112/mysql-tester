package com.wuda.tester.mysql.commons.keygen;

/**
 * 超过最大可生成的key异常.
 *
 * @author wuda
 * @version 1.0
 */
public class KeyGenExceedMaxValueException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message
     *         the detail message. The detail message is saved for
     *         later retrieval by the {@link #getMessage()} method.
     */
    public KeyGenExceedMaxValueException(String message) {
        super(message);
    }
}
