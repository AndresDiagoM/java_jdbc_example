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

public class ProductoController {

    public void modificar(String nombre, String descripcion, Integer id) {
        // TODO
    }

    public void eliminar(Integer id) {
        // TODO
    }

    public List<Map<String, Object>> listar() throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost/control_de_stock?useTimezone=true&serverTimezone=UTC",
                "root", "root");

        Statement query = con.createStatement();

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

        con.close();
        return productos;
    }

    public void guardar(Object producto) {
        // TODO
    }

}
