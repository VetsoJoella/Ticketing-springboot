package com.ticketing.service.pdf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.ticketing.model.reservation.ReservationDTO;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Service
public class PdfService {
    
    @Value("${webservice.images}")
    private String webServiceImg;
    private final TemplateEngine templateEngine;
    
    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    
    public byte[] generateReservationPdf(ReservationDTO response) {

        Context context = new Context();
        context.setVariable("reservation", response);
        context.setVariable("imageBaseUrl", webServiceImg);
        String html = templateEngine.process("pdf/template-reservation.html", context);
        
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }
    }
    

    // Test de service
    public byte[] generatePdf(String nom, Long id, LocalDate date) {
        // Convertir LocalDate en Date si nécessaire
        Date dateConvertie = java.sql.Date.valueOf(date);
        
        // Préparer les données pour le template
        Context context = new Context();
        context.setVariable("document", Map.of(
            "nom", nom,
            "id", id,
            "date", dateConvertie
        ));
        
        // Générer le HTML à partir du template Thymeleaf
        String html = templateEngine.process("pdf/template-reservation.html", context);
        
        // Générer le PDF
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }
    }
}