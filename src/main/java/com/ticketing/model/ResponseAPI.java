package com.ticketing.model;

public class ResponseAPI<T> {
    private int status;
    private String message;
    private String error;
    private T data;

    // Constructeurs
    public ResponseAPI() {
    }

    public ResponseAPI(int status, String message, T data) {
        setStatus(status);
        setMessage(message);
        setData(data);
    }

    public ResponseAPI(int status, String error) {
        setStatus(status);
        setError(error);
    }

    public ResponseAPI(int status, String message, String error, T data) {
        setStatus(status);
        setMessage(message);
        setError(error);
        setData(data);
    }

    // Getters et setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // Méthode utilitaire pour une réponse réussie
    public static <T> ResponseAPI<T> success(int status, String message, T data) {
        return new ResponseAPI<>(status, message, data);
    }

    // Méthode utilitaire pour une réponse d'erreur
    public static <T> ResponseAPI<T> error(int status, String error, T data) {
        return new ResponseAPI<>(status, error, data);
    }
}
