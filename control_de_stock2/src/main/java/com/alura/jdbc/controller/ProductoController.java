package com.alura.jdbc.controller;

import java.util.List;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;
import com.alura.jdbc.DAO.ProductoDAO;

public class ProductoController {

    //---------------- ATRIBUTOS ----------------
    ProductoDAO ProductoDAO;

    //---------------- CONSTRUCTOR ----------------
    public ProductoController() {
        ConnectionFactory ConnectionFactory = new ConnectionFactory();
        this.ProductoDAO = new ProductoDAO(ConnectionFactory.conectar());
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
    public List<Producto> listar(Categoria categoria) {
        return ProductoDAO.listarProducto(categoria.getId());
    }

    public void guardar(Producto producto) {
        ProductoDAO.guardarProducto(producto);
    }


}
