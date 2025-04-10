package com.ticketing.util.date;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

public class DateUtil {
    
     public static Timestamp ajouterHeure(Timestamp timestamp, float duree) {

        Duration duration = Duration.ofMillis((long)(duree*3600000));

        Instant instant = timestamp.toInstant() ;
        instant = instant.plus(duration) ; 

        return Timestamp.from(instant) ;
    }

}
