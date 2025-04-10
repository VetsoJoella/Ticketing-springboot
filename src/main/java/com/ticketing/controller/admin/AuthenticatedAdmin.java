package com.ticketing.controller.admin;

import java.sql.Connection;
import javax.sql.DataSource;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ticketing.model.reservation.AnnulationReservation;
import com.ticketing.model.reservation.Reservation;
import com.ticketing.model.vol.categorie.Categorie;
import com.ticketing.model.vol.categorie.CategorieAge;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/admin")
public class AuthenticatedAdmin {
    
    @Autowired
    private DataSource dataSource; 

    @GetMapping("/categories")
    public ModelAndView categorieModif(Model model) {
        try (Connection connection = dataSource.getConnection()) {
            CategorieAge[] categorieAges = CategorieAge.getAll(connection) ;
            Categorie[] categories = Categorie.getAll(connection) ;
            model.addAttribute("categorieAges", categorieAges) ; 
            model.addAttribute("categories", categories) ; 

        } catch (Exception e) {
            model.addAttribute("message", e.getMessage()) ;
        }
        return new ModelAndView("pages/admin/categorie-modification.html");
    }

    @PostMapping("/categorie")
    public String updtCategorie(@ModelAttribute CategorieAge categorieAge, RedirectAttributes redirectAttributes) {
        
        System.out.println("Appel de update categorie avec id Categorie est ");
        try (Connection connection = dataSource.getConnection()) {
            categorieAge.update(connection);
            redirectAttributes.addFlashAttribute("message", "Mise à jour effectué") ; 
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", e.getMessage()) ; 

        }
        return "redirect:/admin/categories";
    }
    
    @GetMapping("/reservations")
    public String listeReservation(Model model) {
        
        try (Connection connection = dataSource.getConnection()) {
            Reservation[] reservations = Reservation.getAll(connection);
            model.addAttribute("reservations", reservations) ;
            System.out.println("Longeur de reservation est "+reservations.length);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage()) ;
        }
        return "pages/admin/reservations.html";
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
        return "redirect:/admin/reservations" ;
    }

    @GetMapping("/deconnection")
    public String getMethodName(HttpSession session) {
        session.removeAttribute("utilisateur");
        return new String("redirect:/admin/login");
    }
    

    // @GetMapping("vols")
    // public String listVols(HttpSession session) {
    //     // if(session.getAttribute("Utilisateur")) {

    //     // }

    //     // Vol[] vols = Vol.get 
    // }
    
}
