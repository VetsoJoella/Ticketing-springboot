package com.ticketing.exception.sql;

import java.sql.SQLException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AnyRowInsertedException extends SQLException{
    
    public AnyRowInsertedException(String message) {
        super(message) ;
    }

    public AnyRowInsertedException(String message, Throwable throwable) {
        super(message, throwable) ;
    }
    
}
