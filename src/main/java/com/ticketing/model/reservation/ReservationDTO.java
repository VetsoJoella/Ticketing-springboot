package com.ticketing.model.reservation;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ticketing.model.reservation.fille.ReservationFilleDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservationDTO {

    String id;
    String passager;
    @JsonFormat(pattern = "MMM dd, yyyy, h:mm:ss a", locale = "en_US")
    Date dateReservation;
    String image;
    ReservationFilleDTO[] reservationFillesDTO;


    // Getters et setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassager() {
        return passager;
    }

    public void setPassager(String passager) {
        this.passager = passager;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ReservationFilleDTO[] getReservationFillesDTO() {
        return reservationFillesDTO;
    }

    public void setReservationFillesDTO(ReservationFilleDTO[] reservationFillesDTO) {
        this.reservationFillesDTO = reservationFillesDTO;
    }
}