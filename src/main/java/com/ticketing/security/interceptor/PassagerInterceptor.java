package com.ticketing.security.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;
import com.ticketing.model.utilisateur.passager.Passager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PassagerInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        HttpSession session = request.getSession(false); // false = ne pas créer de session si elle n'existe pas
        
        if (session == null) {
            response.sendRedirect("/passager/login");
            return false;
        }
        
        Object utilisateur = session.getAttribute("utilisateur");
        if (utilisateur == null) {
            response.sendRedirect("/passager/login");
            return false;
        }
        
        if (utilisateur instanceof Passager) {
            // response.sendRedirect("/passager/reservations");
            return true;
        }
        return false; // Autoriser la requête
    }
}