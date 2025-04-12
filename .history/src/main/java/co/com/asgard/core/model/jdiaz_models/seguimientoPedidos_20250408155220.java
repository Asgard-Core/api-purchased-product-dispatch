package com.jhonny.api.models.jdiaz_models;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jhonny.api.enums.jdiaz_enum.EstadoSeguimiento;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class seguimientoPedidos {

  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_pedido")
    @JsonManagedReference("pedido-seguimiento")
    private Pedido pedido;

    private String ubicacionActual;

    @Enumerated(EnumType.STRING)
    private EstadoSeguimiento estado;

    
    private Date tiempoEstimadoLlegada;
    
    private  String actualizadoEn;



}
