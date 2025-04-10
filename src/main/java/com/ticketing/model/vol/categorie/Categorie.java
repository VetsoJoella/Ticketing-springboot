package com.ticketing.model.vol.categorie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categorie {

    String id, nom ; 

    public Categorie(String id) {
        setId(id);
    }


    public static Categorie[] getAll(Connection connection) throws Exception {
        
        List<Categorie> listes = new ArrayList<>() ;
        
        String requete = "SELECT * from categorie " ;
        try(PreparedStatement preparedStatement = connection.prepareStatement(requete)) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                listes.add(new Categorie(rs.getString("id"), rs.getString("nom"))) ;
            }
            
        } 
        return listes.toArray(new Categorie[0]) ;
    }

}

