package org.kaleta.scheduler.service;

/**
 * Created by Stanislav Kaleta on 27.07.2015.
 */
public class ServiceFailureException extends RuntimeException {

    public ServiceFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceFailureException(String message) {
        super(message);
    }

    public ServiceFailureException(Throwable cause) {
        super(cause);
    }

    public ServiceFailureException() {
    }
}
