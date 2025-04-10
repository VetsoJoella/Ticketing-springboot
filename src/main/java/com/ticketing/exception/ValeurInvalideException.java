package com.ticketing.exception;

public class ValeurInvalideException extends Exception {
    // Constructeur avec un message personnalisé
    public ValeurInvalideException(String message) {
        super(message);
    }

    // Constructeur avec un message par défaut
    public ValeurInvalideException() {
        super("La valeur fournie est invalide.");
    }
}