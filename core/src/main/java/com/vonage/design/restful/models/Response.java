package com.vonage.design.restful.models;

/**
 * Model bean for a restful web-service response.
 *
 * @author Vonage
 */
public class Response {

    /**
     * Status code
     */
    private int statusCode;

    /**
     * Response body
     */
    private String body;

    /**
     * Constructor
     */
    public Response() {
        // Do nothing
    }

    /**
     * Get status code
     *
     * @return int - integer
     */
    public final int getStatusCode() {
        return this.statusCode;
    }

    /**
     * Set status code
     *
     * @param statusCode - integer
     */
    public final void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Get body
     *
     * @return String body
     */
    public final String getBody() {
        return this.body;
    }

    /**
     * Set body
     *
     * @param body - String
     */
    public final void setBody(final String body) {
        this.body = body;
    }
}
