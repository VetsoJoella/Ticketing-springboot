package com.ticketing.exception.model.reservation;


public class ReservationException extends Exception {

    Object errorData;

    public ReservationException(String message, Object errorData) {
        super(message);
        this.errorData = errorData;
    }

    public ReservationException(String message, Object errorData, Throwable cause) {
        super(message, cause);
        this.errorData = errorData;
    }

    public Object getErrorData() {
        return errorData;
    }

    @Override
    public String toString() {
        return "ReservationException{" +
               "message=" + getMessage() +
               ", errorData=" + errorData +
               '}';
    }
}