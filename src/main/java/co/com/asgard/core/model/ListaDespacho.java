package co.com.asgard.core.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class ListaDespacho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String destino;
    private LocalDateTime fechaCreacion;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<DetalleDespacho> detalles;
    
    private String comprobanteSalida;
}