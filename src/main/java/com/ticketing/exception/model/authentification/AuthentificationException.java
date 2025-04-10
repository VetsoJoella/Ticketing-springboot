package com.ticketing.exception.model.authentification;

import com.ticketing.model.utilisateur.Utilisateur;

public class AuthentificationException extends Exception {
    private Utilisateur utilisateur;

    // Constructeur avec message
    public AuthentificationException(Utilisateur utilisateur, String message) {
        super(message);
        this.setUtilisateur(utilisateur);
    }

    // Constructeur sans message
    public AuthentificationException(Utilisateur utilisateur) {
        this(utilisateur, "Authentification échouée.");
    }

    // Getter et Setter
    public Utilisateur getutilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}