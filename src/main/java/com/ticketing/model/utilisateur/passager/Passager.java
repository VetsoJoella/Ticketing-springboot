package com.ticketing.model.utilisateur.passager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ticketing.exception.model.authentification.AuthentificationException;
import com.ticketing.model.utilisateur.Utilisateur;


public class Passager extends Utilisateur{
    
    String numero, nom ; 

    // Contructeur 
    public Passager(){}

    public Passager(String id){
        setId(id);
    }

    public Passager(String numero, String nom) {

        setNumero(numero); setNom(nom);
    }

    public Passager(String id, String numero, String nom) {

        setId(id); setNom(nom); setNumero(numero);
    }

    // Getters and setters 
    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String id) {
        this.numero = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Méthode pour récupérer un Admin par son login
    public Passager getByNumero(Connection connection) throws Exception {
        
        String query = "SELECT * FROM passager WHERE nom = ? and numero = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, getNom());
            stmt.setString(2, getNumero());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Passager(rs.getString("id"), rs.getString("numero"), rs.getString("nom"));
            } 
            return null ;
        }
    }

   
    public static Passager getById(Connection connection, String id) throws Exception {
        
        String query = "SELECT * FROM passager WHERE id = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Passager(rs.getString("id"), rs.getString("numero"),rs.getString("nom"));
            } 
            return null ;
        }
    }

   
    // Méthode pour se connecter
    public void se_connecter(Connection connection) throws Exception {

        Passager passager = null;
    
        passager = getByNumero(connection) ;
        if(passager==null) throw new AuthentificationException(null, "Login ou mot de passe incorrect.");
        setId(passager.getId());
        System.out.println("Utilisateur bien authentifié "+passager.toString());
      
    }

    public void creerCompte(Connection connexion) throws Exception {
        
        String query = "INSERT INTO passager values(default, ?, ?) ";
        try (PreparedStatement stmt = connexion.prepareStatement(query,  Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, getNom());
            stmt.setString(2, getNumero());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    setId(generatedKeys.getString(1));
                } else {
                    throw new SQLException("Échec de la récupération de l'ID généré.");
                }
            }
        }
    }


    @Override
    public String toString() {
        return "{" +
            " id = "+ getId()+
            ", numero='" + getNumero() + "'" +
            ", nom='" + getNom() + "'" +
            "}";
    }


}
