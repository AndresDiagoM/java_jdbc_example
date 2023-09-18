package com.alura.jdbc.controller;

import java.sql.SQLException;
import java.util.List;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;
import com.alura.jdbc.DAO.ProductoDAO;

public class ProductoController {

    //---------------- ATRIBUTOS ----------------
    ConnectionFactory crearConexion = new ConnectionFactory();
    ProductoDAO persistenciaProducto;

    //---------------- CONSTRUCTOR ----------------
    public ProductoController() throws SQLException {
        this.persistenciaProducto = new ProductoDAO();
    }

    //---------------- METODOS ----------------
    public void modificar(String nombre, String descripcion, Integer id) throws SQLException {
        persistenciaProducto.modificar(nombre, descripcion, id);
    }

    public int eliminar(Integer id) throws SQLException {
        return persistenciaProducto.eliminar(id);
    }

    public List<Producto> listar() throws SQLException {
        return persistenciaProducto.listarProducto();
    }

    public void guardar(Producto producto) throws SQLException {
        persistenciaProducto.guardarProducto(producto);
    }

}
