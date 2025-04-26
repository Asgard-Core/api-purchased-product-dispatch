package co.com.asgard.core.controller;

import co.com.asgard.core.model.DetalleDespacho;
import lombok.Data;
import java.util.List;

@Data
public class CrearListaRequest {
    private String destino;
    private List<DetalleDespacho> detalles;
}