package co.com.asgard.core.service;

import co.com.asgard.core.model.Producto;

public interface IStockService {

    Producto buscarProducto(String query);

    Producto actualizarStock(String codigo, int cantidadNueva);

    void guardarConsulta(String usuario, String producto);

}