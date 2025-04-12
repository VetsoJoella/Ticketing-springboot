package com.ticketing.controller.client;

import java.sql.Connection; 

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ticketing.model.utilisateur.passager.Passager;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/client")
public class NavigationControllerClient {
    
    @Autowired
    private DataSource dataSource; 
    
    @GetMapping("/login")
    public ModelAndView login() {
        System.out.println("Appel de login controller");
        return new ModelAndView("pages/client/login.html");
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute Passager passager, HttpSession session, RedirectAttributes redirectAttributes) {

        try(Connection connection = dataSource.getConnection()) {
            passager.se_connecter(connection);
            session.setAttribute("utilisateur", passager);  
            return "redirect:/client/reservations";
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", e.getMessage()) ;
            return "redirect:/client/login";
        }
    }

}
