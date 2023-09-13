package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;

public class ProductoController {
    
    ConnectionFactory crearConexion = new ConnectionFactory();
    Connection conexion = crearConexion.conectar();

    public void modificar(String nombre, String descripcion, Integer id) {
        // TODO
    }

    public void eliminar(Integer id) {
        // TODO
    }

    public List<Map<String, Object>> listar() throws SQLException {

        Statement query = conexion.createStatement();

        //probar si se pude hacer consultas
//        boolean result = query.execute("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
//        System.out.println("[ProductoController] Listar:" + result);
        
        //guardar productos en una lista
        ResultSet resultSet = query.executeQuery("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");

        List<Map<String, Object>> productos = new ArrayList<>();

        while (resultSet.next()) {
            Map<String, Object> producto = new HashMap<>();
            producto.put("ID", resultSet.getInt("ID"));
            producto.put("NOMBRE", resultSet.getString("NOMBRE"));
            producto.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
            producto.put("CANTIDAD", resultSet.getInt("CANTIDAD"));
            productos.add(producto);
            System.out.println("[ProductoController] Listar:" + producto);
        }

        conexion.close();
        return productos;
    }

    public void guardar(Object producto) {
        // TODO
    }

}
