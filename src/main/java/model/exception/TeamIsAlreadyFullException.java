package main.java.model.exception;

public class TeamIsAlreadyFullException extends RuntimeException {
    public TeamIsAlreadyFullException(String errorMessage) {
        super(errorMessage);
    }
}
