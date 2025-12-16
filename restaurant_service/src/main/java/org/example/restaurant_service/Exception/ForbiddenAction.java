package org.example.restaurant_service.Exception;

public class ForbiddenAction extends RuntimeException {
    public ForbiddenAction(String message) {
        super(message);
    }
}
