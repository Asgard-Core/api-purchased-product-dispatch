package co.com.asgard.core.model;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int stock;
    private String descripcion;
}