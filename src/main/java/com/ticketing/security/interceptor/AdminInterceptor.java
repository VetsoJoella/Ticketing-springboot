package com.ticketing.security.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;

import com.ticketing.model.utilisateur.admin.Admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AdminInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        HttpSession session = request.getSession(false); // false = ne pas créer de session si elle n'existe pas
        
        if (session == null) {
            response.sendRedirect("/admin/login");
            return false;
        }
        
        Object utilisateur = session.getAttribute("utilisateur");
        if (utilisateur == null) {
            response.sendRedirect("/admin/login");
            return false;
        }
        
        if (utilisateur instanceof Admin) {
            // response.sendRedirect("/admin/reservations");
            return true;
        }
        return false; // Autoriser la requête
    }
}