package com.ticketing.service.reservation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ticketing.model.ResponseAPI;
import com.ticketing.model.reservation.ReservationDTO;

@Service
public class ReservationService {

    @Value("${webservice.url}")
    private String webServiceUrl;
    
    private final RestTemplate restTemplate;

    public ReservationService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    // public ReservationDTO fetchReservationById(String id) {
    //     String url = "http://localhost:8080/Ticketing/api/reservation?id=" + id;
    //     return restTemplate.getForObject(url, ReservationDTO.class);
    // }

    public ResponseAPI<ReservationDTO> fetchReservationById(String id) {
        String url = webServiceUrl+"reservation?idReservation=" + id;
        ResponseEntity<ResponseAPI<ReservationDTO>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<ResponseAPI<ReservationDTO>>() {}
        );

        return response.getBody();
    }
}
