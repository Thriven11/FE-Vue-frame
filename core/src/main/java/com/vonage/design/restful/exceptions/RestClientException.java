package com.vonage.design.restful.exceptions;

/**
 * Rest client exception
 *
 * @author Vonage
 */
public class RestClientException extends Exception {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 8203732109886290287L;

    /**
     * Exception Constructor.
     *
     * @param message String
     */
    public RestClientException(final String message) {
        super(message);
    }
}
