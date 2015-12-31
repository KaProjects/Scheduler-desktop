package org.kaleta.scheduler.backend.manager;

/**
 *  Created by Stanislav Kaleta on 23.07.2015.
 */
public class ManagerException extends Exception {

    public ManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ManagerException(String message) {
        super(message);
    }

    public ManagerException(Throwable cause) {
        super(cause);
    }

    public ManagerException() {
    }
}
