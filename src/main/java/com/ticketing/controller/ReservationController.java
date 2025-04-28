package com.ticketing.controller;


import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ticketing.model.ResponseAPI;
import com.ticketing.model.reservation.ReservationDTO;
import com.ticketing.service.pdf.PdfService;
import com.ticketing.service.reservation.ReservationService;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@Controller 
@RequestMapping("/reservation")
public class ReservationController {
    
    @Autowired
    ReservationService reservationService; 
    @Autowired
    PdfService pdfGeneratorService ;

    @GetMapping("/import")
    public  ResponseEntity<ByteArrayResource> getReservation(@RequestParam String id) {

        try {
            ResponseAPI<ReservationDTO> res = reservationService.fetchReservationById(id);
        
            byte[] pdfBytes = pdfGeneratorService.generateReservationPdf((ReservationDTO)res.getData());
            
            // Préparer la réponse
            ByteArrayResource resource = new ByteArrayResource(pdfBytes);
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=document.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(pdfBytes.length)
                    .body(resource);
        } catch (Exception e) {
           e.printStackTrace();
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new ByteArrayResource(null));
        }
       
    }

    // @GetMapping("/import")
    // public String getReservation(@RequestParam String id, Model model) {

    //     try {
    //         ResponseAPI<ReservationDTO> res = reservationService.fetchReservationById(id);
    //         model.addAttribute("reservation", (ReservationDTO)res.getData()) ;
    //         byte[] pdfBytes = pdfGeneratorService.generateReservationPdf((ReservationDTO)res.getData());
            
    //         // Préparer la réponse
    //         ByteArrayResource resource = new ByteArrayResource(pdfBytes);
            
    //        return "pdf/template-reservation.html" ;
    //     } catch (Exception e) {
    //         return "redirect:/admin/reservations" ;
    //     }
       
    // }

    // @GetMapping("/import")
    // public ReservationDTO getReservation(@RequestParam String id) {

    //     ResponseAPI<ReservationDTO> res = reservationService.fetchReservationById(id);
    //     return (ReservationDTO)res.getData() ; 
    // }

    // Test de pdf
    @GetMapping("/generate-pdf")
    public ResponseEntity<ByteArrayResource> generatePdf() {
        
        String nom = "Test" ; Long id = Long.valueOf("1234") ; String date = "2021-12-12" ; 
        // Convertir la date String en LocalDate
        LocalDate localDate = LocalDate.parse(date);
        
        // Générer le PDF
        byte[] pdfBytes = pdfGeneratorService.generatePdf(nom, id, localDate);
        
        // Préparer la réponse
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=document.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfBytes.length)
                .body(resource);
    }

    
}
