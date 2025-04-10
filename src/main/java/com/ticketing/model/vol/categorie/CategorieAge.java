package com.ticketing.model.vol.categorie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ticketing.exception.ValeurInvalideException;
import com.ticketing.exception.sql.AnyRowInsertedException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorieAge {
    
    String id; 
    Categorie categorie ; 
    // @Min(value = 0, message = "L'âge minimum doit être positif")
    int ageMin; 
    // @Min(value = 0, message = "L'âge minimum doit être positif")
    int ageMax ; 
    double promotion ; 
    
    public CategorieAge(String id) {
        setId(id);
    }

    void setAgeMin(Integer valeur) throws ValeurInvalideException {
        if(valeur<0) throw new ValeurInvalideException("Valeur de age min invalide") ;
        this.ageMin = valeur ;
    }

    void setAgeMax(Integer valeur) throws ValeurInvalideException {
        this.ageMax = valeur ;
    }

    public void setAgeMin(String valeur) throws ValeurInvalideException {
        try{
            System.out.println("Valeur de age min est "+valeur);
            if(valeur.isEmpty() || valeur.isBlank() || valeur==null) setAgeMin(0);
            else { 
                int val = Integer.valueOf(valeur) ;
                setAgeMin(val);
            }
        } catch(Exception exception){
            throw new ValeurInvalideException("Valeur de age invalide") ;
        }
    }
    
    public void setAgeMax(String valeur) throws ValeurInvalideException {
        try{
            System.out.println("Valeur de age max est "+valeur);
            if(valeur.isEmpty() || valeur.isBlank() || valeur==null) setAgeMax(100);
            else { 
                int val = Integer.valueOf(valeur) ;
                setAgeMax(val);
            }
        } catch(Exception exception){
            throw new ValeurInvalideException("Valeur de age invalide") ;
        }
    }

    public void setCategorie(String id) throws Exception {
        // System.out.println("Catégorie ID est "+id);
        setCategorie(new Categorie(id));
    }

    // public void setCategorie(Connection connection, String id) throws Exception {
    //     setCategorie(Categorie.getById(connection, id));
    // }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie ; 
    }

    public void setPromotion(double promotion) throws ValeurInvalideException{
        if(promotion<0) throw new ValeurInvalideException("Valeur de promotion invalide") ;
        this.promotion = promotion ;

    }

    public void setPromotion(String valeur) throws ValeurInvalideException {
        try {
            double d = Double.valueOf(valeur) ;
            setPromotion(d);
        } catch (Exception e) {
            throw new ValeurInvalideException("Valeur de promotion non numérique") ;
        }
    }


    public static CategorieAge[] getAll(Connection connection) throws Exception {

        List<CategorieAge> listes = new ArrayList<>() ;
        
        String requete = "SELECT * from v_categorie_age_detail" ;
        try(PreparedStatement preparedStatement = connection.prepareStatement(requete)) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CategorieAge categorieAge = new CategorieAge(rs.getString("id")) ;
                categorieAge.setCategorie(new Categorie(rs.getString("idCategorie"), rs.getString("nom")));
                categorieAge.setAgeMin(rs.getInt("ageMin"));
                categorieAge.setAgeMax(rs.getInt("ageMax"));
                categorieAge.setPromotion(rs.getDouble("promotion"));
                listes.add(categorieAge);
            }  
        } 
        return listes.toArray(new CategorieAge[0]) ;
    }

    public static CategorieAge getById(Connection connection, String id) throws Exception {
        
        String requete = "SELECT * from v_categorie_age_detail WHERE id = ?" ;
        try(PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CategorieAge categorieAge = new CategorieAge(rs.getString("id")) ;
                categorieAge.setCategorie(new Categorie(rs.getString("idCategorie"), rs.getString("nom")));
                categorieAge.setAgeMin(rs.getInt("ageMin"));
                categorieAge.setAgeMax(rs.getInt("ageMax"));
                categorieAge.setPromotion(rs.getDouble("promotion"));
                return categorieAge ;
            }  
        } 
        return null ;
    }

    public static CategorieAge getByCategorie(Connection connection, String idCategorie) throws Exception {
        
        String requete = "SELECT * from v_categorie_age_detail WHERE idCategorie = ?" ;
        try(PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setString(1, idCategorie);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CategorieAge categorieAge = new CategorieAge(rs.getString("id")) ;
                categorieAge.setCategorie(new Categorie(rs.getString("idCategorie"), rs.getString("nom")));
                categorieAge.setAgeMin(rs.getInt("ageMin"));
                categorieAge.setAgeMax(rs.getInt("ageMax"));
                categorieAge.setPromotion(rs.getDouble("promotion"));
                return categorieAge ;
            }  
        } 
        return null ;
    }

    void insertHistorique(Connection connection) throws Exception {

        String requete = "INSERT INTO historiqueCategorieAge(id, idCategorie, ageMin, ageMax, promotion) VALUES (DEFAULT,?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setString(1, getCategorie().getId());
            preparedStatement.setInt(2, getAgeMin());
            preparedStatement.setInt(3, getAgeMax());
            preparedStatement.setDouble(4, getPromotion());
            preparedStatement.executeUpdate() ;
        } 
    }

    void updateCategorie(Connection connection) throws Exception {
        String requete = "UPDATE categorieAge set ageMin = ?, ageMax = ?, promotion = ? WHERE idCategorie = ?" ;
        try(PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            
            preparedStatement.setInt(1, getAgeMin());
            preparedStatement.setInt(2, getAgeMax());
            preparedStatement.setDouble(3, getPromotion());
            preparedStatement.setString(4, getCategorie().getId());
            if(preparedStatement.executeUpdate()==0) throw new AnyRowInsertedException("Aucune ligne mis à jour");

        } 
    }

    void insert(Connection connection) throws Exception {
        String requete = "INSERT INTO categorieAge(id, idCategorie, ageMin, ageMax, promotion) VALUES (DEFAULT,?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setString(1, getCategorie().getId());
            preparedStatement.setInt(2, getAgeMin());
            preparedStatement.setInt(3, getAgeMax());
            preparedStatement.setDouble(4, getPromotion());
            if(preparedStatement.executeUpdate()==0) throw new SQLException("Aucune ligne mis à jour");

        } 
    }

    public void update(Connection connection) throws Exception {

        System.out.println("Categorie est : "+getCategorie().getId());
        connection.setAutoCommit(false);
        try {
            updateCategorie(connection);
            insertHistorique(connection);
            connection.commit();
        } catch (AnyRowInsertedException e) {
            connection.rollback(); 
            try {
                insert(connection);
                insertHistorique(connection);
                connection.commit();
            } catch (Exception retryEx) {
                connection.rollback();
                throw retryEx; // Ou encapsuler avec e.addSuppressed(retryEx)
            }
        } catch (Exception e) {
            connection.rollback();
            throw e; // Ou throw new CustomException("Transaction failed", e);
        } 
        connection.setAutoCommit(true);
    }

}

// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// class Categorie {

//     String id, nom ; 

//     public Categorie(String id) {
//         setId(id);
//     }

// }