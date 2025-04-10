package com.ticketing.model.utilisateur;

import java.sql.Connection;


public abstract class Utilisateur {
    String id ; 

     public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public abstract void se_connecter(Connection connection) throws Exception;
}
