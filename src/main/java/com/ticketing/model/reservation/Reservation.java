package com.ticketing.model.reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ticketing.model.utilisateur.passager.Passager;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    
    String id ;
    Passager passager ; 
    Timestamp dateReservation;

    public Reservation(String id) {
        setId(id);
    }
    
    public static Reservation[] getAll(Connection connexion) throws Exception {

        List<Reservation> listes = new ArrayList<>() ;
        String requete = "SELECT * from reservation WHERE id not in (SELECT idReservation from historiqueannulation) " ; 

        try (PreparedStatement declaration = connexion.prepareStatement(requete)) {    
            ResultSet resultat = declaration.executeQuery();
            Reservation reservation = null ; 

            while(resultat.next()) {
               reservation = new Reservation(
                resultat.getString("id"),
                Passager.getById(connexion, resultat.getString("idPassager")),
                resultat.getTimestamp("datereservation")
               ) ;
               listes.add(reservation) ;
            }
        }
        return listes.toArray(new Reservation[0]) ;

    }

    public static Reservation[] getByPassager(Connection connexion, Passager passager) throws Exception {
        
        List<Reservation> listes = new ArrayList<>() ;
        String requete = "SELECT * from reservation WHERE id not in (SELECT idReservation from historiqueannulation) and idPassager = ?" ; 
        System.out.println("Id passager est "+passager.getId());
        try (PreparedStatement declaration = connexion.prepareStatement(requete)) {    
            declaration.setString(1, passager.getId());
            ResultSet resultat = declaration.executeQuery();
            Reservation reservation = null ; 

            while(resultat.next()) {
               reservation = new Reservation(
                resultat.getString("id"),
                new Passager(resultat.getString("idPassager")),
                resultat.getTimestamp("datereservation")
               ) ;
               listes.add(reservation) ;
            }
        }
        return listes.toArray(new Reservation[0]) ;
    }

    public void deleteFille(Connection connection)throws Exception{
        
        String requete = "DELETE FROM reservationFille where idReservation = ? " ;
        try (PreparedStatement stmt = connection.prepareStatement(requete)) {
            stmt.setString(1, getId());
            stmt.executeUpdate() ;
        } 
    }

}

