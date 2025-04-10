package com.ticketing.model.utilisateur.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ticketing.exception.model.authentification.AuthentificationException;
import com.ticketing.model.utilisateur.Utilisateur;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Admin extends Utilisateur{

    private String login;
    private String mdp;

    // Constructeur
    public Admin(String login, String mdp) {
        setLogin(login);
        setMdp(mdp);
    }

    public Admin(String id, String login, String mdp) {
        setId(id);
        setLogin(login);
        setMdp(mdp);
    }

    public Admin(){}

    // Getters et Setters
    // public String getId() {
    //     return id;
    // }

    // public void setId(String id) {
    //     this.id = id;
    // }

    // Méthode pour récupérer un Admin par son login
    public static Admin getByLogin(Connection connection, String login, String mdp) throws Exception {
        
        String query = "SELECT * FROM Admin WHERE login = ? and mdp = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, login);
            stmt.setString(2, mdp);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Admin(rs.getString("id"), rs.getString("login"), rs.getString("mdp"));
            } 
            return null ;
        }
    }

    // Méthode pour se connecter
    public void se_connecter(Connection connection) throws Exception {

        Admin admin = null;
    
        admin = getByLogin(connection, getLogin(), getMdp()) ;
        if(admin==null) throw new AuthentificationException(this, "Login ou mot de passe incorrect.");
        setId(admin.getId());
      
    }


    @Override
    public String toString() {
        return "{" +
            " login='" + getLogin() + "'" +
            ", mdp='" + getMdp() + "'" +
            "}";
    }

}