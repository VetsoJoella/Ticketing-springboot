package com.ticketing.controller.client;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ticketing.model.reservation.AnnulationReservation;
import com.ticketing.model.reservation.Reservation;
import com.ticketing.model.utilisateur.passager.Passager;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;


@Controller
@RequestMapping("/client")
public class AuthenticatedClient {
    
    @Autowired
    private DataSource dataSource; 

    @GetMapping("/reservations")
    public String listeReservations(HttpSession session, Model model) {
        try (Connection connection = dataSource.getConnection()) {
            
            Passager passager = (Passager)session.getAttribute("utilisateur");
            Reservation[] reservations = Reservation.getByPassager(connection, passager) ;
            model.addAttribute("reservations", reservations) ;
            System.out.println("Longeur de reservation est "+reservations.length);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage()) ;
        }
        return new String("pages/client/reservations.html");
    }

    @GetMapping("/reservation")
    public String annulationReservation(@RequestParam String id, RedirectAttributes redirectAttributes) {

        try (Connection connection = dataSource.getConnection()) {
            
            AnnulationReservation annulationReservation = new AnnulationReservation();
            annulationReservation.setReservation(id);
            annulationReservation.setDateAnnulation(connection);

            annulationReservation.confirmer(connection);
            redirectAttributes.addFlashAttribute("message", "Annulation effectuée") ;

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage()) ;

        }
        return "redirect:/client/reservations" ;
    }

    @GetMapping("/deconnection")
    public String getMethodName(HttpSession session) {
        session.removeAttribute("utilisateur");
        return new String("redirect:/client/login");
    }
    
    
}
