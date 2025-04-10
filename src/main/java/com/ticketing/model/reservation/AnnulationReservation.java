package com.ticketing.model.reservation;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.ticketing.exception.model.reservation.ReservationException;
import com.ticketing.util.date.DateUtil;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnnulationReservation {
    
    Timestamp dateAnnulation ; 
    Reservation reservation ;

    public void setDateAnnulation(String timeString) throws ReservationException {
        try {
            Timestamp timestamp = Timestamp.valueOf(timeString) ;
            setDateAnnulation(timestamp);
        } catch (Exception e) {
            setDateAnnulation(Timestamp.valueOf(LocalDateTime.now()));
        }
    }

    void setDateAnnulation(Timestamp time) throws ReservationException {
        this.dateAnnulation = time;

    }
    public void setDateAnnulation(Connection connection) throws Exception{
        setDateAnnulation(connection, Timestamp.valueOf(LocalDateTime.now()));
    }
    public void setDateAnnulation(Connection connection, Timestamp time) throws Exception {
        
        HoraireVol horaireVol = getHoraireAnnulation(connection) ;
        Timestamp derniereAnnulation = DateUtil.ajouterHeure(horaireVol.getDateDecollage(), -horaireVol.getDerniereAnnulation());
        
        if(time==null) time = Timestamp.valueOf(LocalDateTime.now());
        if(time.after(derniereAnnulation) || time.equals(derniereAnnulation)) throw new ReservationException("Impossible d'annuler la réservation : la date de dernère annulation est déjà dépassée", getReservation());
        setDateAnnulation(time);
    }

    public void setReservation(String id) {
        setReservation(new Reservation(id));
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation ;
    }

    public HoraireVol getHoraireAnnulation(Connection connection) throws Exception {
        
        HoraireVol horaireVol = new HoraireVol() ;
        String requete = "SELECT * FROM v_vol_reservation where idReservation = ? ";

        try(PreparedStatement pStatement = connection.prepareStatement(requete)) {
            pStatement.setString(1, getReservation().getId()); 

            ResultSet resultat = pStatement.executeQuery();

            if(resultat.next()) {
               horaireVol.setDerniereReservation(resultat.getFloat("d_dernierereservation"));
               horaireVol.setDerniereAnnulation(resultat.getFloat("d_derniereannulation"));
               horaireVol.setDateDecollage(resultat.getTimestamp("datedecollage"));
            }

        } 
        return horaireVol ;
    }

    void insertHistoriqueAnnulation(Connection connection) throws Exception {

        String requete = "INSERT INTO historiqueAnnulation(id, dateAnnulation, idReservation) VALUES (DEFAULT, ?, ?)" ;
        try (PreparedStatement declaration = connection.prepareStatement(requete)) {    
           
            declaration.setTimestamp(1, getDateAnnulation());
            declaration.setString(2, getReservation().getId());
    
            declaration.executeUpdate(); 
        } 
    }

    public void confirmer(Connection connection) throws Exception {

        connection.setAutoCommit(false);
        try {
            reservation.deleteFille(connection);
            insertHistoriqueAnnulation(connection);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        }
        connection.setAutoCommit(true);
    } 

}

@Getter
@Setter
@NoArgsConstructor
class HoraireVol {

    private float derniereAnnulation;
    private float derniereReservation;
    private Timestamp dateDecollage;

    public HoraireVol(Timestamp dateDecollage, float derniereAnnulation, float derniereReservation) {
       
       setDateDecollage(dateDecollage); 
       setDerniereAnnulation(derniereAnnulation);
       setDerniereReservation(derniereReservation);
    }
    // Méthodes utilitaires
    // public boolean peutAnnuler() {
    //     return derniereAnnulation == null || 
    //            derniereAnnulation.isBefore(Timestamp.now().minusHours(2));
    // }

    // public boolean peutReserver() {
    //     return derniereReservation == null || 
    //            derniereReservation.isBefore(Timestamp.now().minusHours(1));
    // }

    @Override
    public String toString() {
        return "HoraireVol{" +
               "derniereAnnulation=" + derniereAnnulation +
               ", derniereReservation=" + derniereReservation +
               '}';
    }
}
