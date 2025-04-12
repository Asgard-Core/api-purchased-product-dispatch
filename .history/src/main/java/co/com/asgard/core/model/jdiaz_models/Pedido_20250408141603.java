package com.jhonny.api.models.jdiaz_models;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jhonny.api.enums.jdiaz_enum.EstadoPedido;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Pedido {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
    
    private Long id;
    private String cliente;
    private String direccionEntrega;
    
    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @OneToMany(mappedBy = "pedido")
    @JsonIgnore
    private List<asignacionesPedidos> asignaciones;

    @OneToOne(mappedBy = "pedido")
   @JsonBackReference("pedido-seguimiento")
    private seguimientoPedidos seguimiento;

   @Temporal(TemporalType.TIMESTAMP)
    private Date creadoEn;



}
