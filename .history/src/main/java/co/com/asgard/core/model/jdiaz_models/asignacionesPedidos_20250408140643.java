package com.jhonny.api.models.jdiaz_models;



import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jhonny.api.enums.jdiaz_enum.EstadoAsignacion;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class asignacionesPedidos {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_transportista")
    @JsonBackReference
    private transportistas transportista;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    @JsonManagedReference("asignacionesPedidos")
    private Pedido pedido;

    @Enumerated(EnumType.STRING)
    private EstadoAsignacion estado;

   
    private  String actualizadoEn;
    // Getters y Setters
}

