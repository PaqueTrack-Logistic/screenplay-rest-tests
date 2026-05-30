package co.edu.udea.calidad.paquetrack.models;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Modelo de solicitud de creacion de envio (se serializa a JSON en el body).
 * POJO con @Builder (buena practica del patron, como BillingData del taller).
 */
@Getter
@Builder
public class ShipmentRequest {

    private String senderName;
    private String senderAddress;
    private String senderCity;
    private String recipientName;
    private String recipientAddress;
    private String recipientCity;
    private BigDecimal weightKg;
}
