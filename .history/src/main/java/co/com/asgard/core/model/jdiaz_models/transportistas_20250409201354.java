package co.com.asgard.core.model.jdiaz_models;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import co.com.asgard.core.enums.jdiaz_enum.*;;


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
@Getter
@Setter


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

    @OneToMany(mappedBy = "transportista")
    @JsonManagedReference
    private List<asignacionesPedidos> asignaciones;

    private  String creadoEn;
}
