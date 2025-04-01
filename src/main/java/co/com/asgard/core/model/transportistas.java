package co.com.asgard.core.model;

import java.util.List;

import co.com.asgard.core.enums.EstadoTransportista;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;



@Entity
@Setter
@Getter

public class transportistas {
    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    
    private long id;
    private String nombre;
    private String telefono;
    private String correo;
    private String placaVehiculo;

     @Enumerated(EnumType.STRING)
    private EstadoTransportista estado;

    @OneToMany(mappedBy = "transportista", cascade = CascadeType.ALL)
    private List<asignacionesPedidos> asignaciones;

    private  String creadoEn;
}
