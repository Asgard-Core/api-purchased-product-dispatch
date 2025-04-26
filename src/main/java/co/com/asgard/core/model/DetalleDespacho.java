package co.com.asgard.core.model;

import lombok.Data;
import jakarta.persistence.*;

/* Pendiente de arreglar el reporte por Excel */

@Entity
@Data
public class DetalleDespacho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Producto producto;
    private int cantidad;
}