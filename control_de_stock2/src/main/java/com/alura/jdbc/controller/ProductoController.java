package com.alura.jdbc.controller;

import java.sql.SQLException;
import java.util.List;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;
import com.alura.jdbc.DAO.ProductoDAO;

public class ProductoController {

    //---------------- ATRIBUTOS ----------------
    ConnectionFactory crearConexion = new ConnectionFactory();
    ProductoDAO ProductoDAO;

    //---------------- CONSTRUCTOR ----------------
    public ProductoController() {
        this.ProductoDAO = new ProductoDAO();
    }

    //---------------- METODOS ----------------
    public void modificar(String nombre, String descripcion, Integer id) {
        ProductoDAO.modificar(nombre, descripcion, id);
    }

    public int eliminar(Integer id) {
        return ProductoDAO.eliminar(id);
    }

    public List<Producto> listar() {
        return ProductoDAO.listarProducto();
    }

    public void guardar(Producto producto) {
        ProductoDAO.guardarProducto(producto);
    }

}
