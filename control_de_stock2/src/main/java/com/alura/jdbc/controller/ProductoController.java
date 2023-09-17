package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

    public void modificar(String nombre, String descripcion, Integer id) throws SQLException {
        final Connection conexion = crearConexion.conectar();
        try(conexion){
            PreparedStatement statement = conexion.prepareStatement("UPDATE PRODUCTO SET NOMBRE = ?, DESCRIPCION = ? WHERE ID = ?");
            try(statement){
                statement.setString(1, nombre);
                statement.setString(2, descripcion);
                statement.setInt(3, id);
                statement.execute();
            }
        }
    }

    public int eliminar(Integer id) throws SQLException {
        // consultar si el producto existe en la base de datos
        final Connection conexion = crearConexion.conectar();
        try(conexion){
            PreparedStatement statement = conexion.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
            statement.setInt(1, id);
    
            int cantidadDeRegistrosAfectados = statement.getUpdateCount();
            System.out.println("[ProductoController] Cantidad de eliminados:" + cantidadDeRegistrosAfectados);
            
            return cantidadDeRegistrosAfectados;
        }
    }

    public List<Map<String, Object>> listar() throws SQLException {
        final Connection conexion = crearConexion.conectar();

        List<Map<String, Object>> productos = new ArrayList<>();
        try(conexion){
            //guardar productos en una lista
            PreparedStatement query = conexion.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
            ResultSet resultSet = query.executeQuery();
            try(query){
                try(resultSet){
                    while (resultSet.next()) {
                        Map<String, Object> producto = new HashMap<>();
                        producto.put("ID", resultSet.getInt("ID"));
                        producto.put("NOMBRE", resultSet.getString("NOMBRE"));
                        producto.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
                        producto.put("CANTIDAD", resultSet.getInt("CANTIDAD"));
                        productos.add(producto);
                        System.out.println("[ProductoController] Listar:" + producto);
                    }
                }
            }
        }
        return productos;
    }

    public void guardar(Map<String, String> producto) throws SQLException {
        // hacer insert en la tabla de productos
        String nombre = producto.get("NOMBRE");
        String descripcion = producto.get("DESCRIPCION");
        Integer cantidad = Integer.valueOf(producto.get("CANTIDAD"));
        Integer maximaCantidad = 50;

        final Connection conexion = crearConexion.conectar();
        
        try(conexion){
            conexion.setAutoCommit(false); //para que no se haga commit automatico
            //evitar inyeccion sql con prepared statement
            String preparedQuery = "INSERT INTO PRODUCTO (NOMBRE, DESCRIPCION, CANTIDAD) VALUES (?, ?, ?)";
            final PreparedStatement preparedStatement = conexion.prepareStatement(preparedQuery, Statement.RETURN_GENERATED_KEYS);
    
            try(preparedStatement){ // esto hace que se cierre el prepared statement automaticamente
                do{ // cada 50 productos se hace un insert
                    Integer cantidadInt = Math.min(cantidad, maximaCantidad);
                    ejecutaRegistro(nombre, descripcion, cantidadInt, preparedStatement, producto);
                    cantidad = cantidad - maximaCantidad;
                }while(cantidad > 0);
                conexion.commit(); //hacer commit manual en base de datos
                System.out.println("[ProductoController] Commit exitodo JDBC:" + preparedStatement);
            } catch (Exception e) {
                conexion.rollback(); //hacer rollback manual en base de datos
                //un rollback es para que no se haga ningun cambio en la base de datos
                System.out.println("[ProductoController] Commit fallido JDBC:" + preparedStatement);
                throw new RuntimeException(e);
            }
        } // esto hace que se cierre la conexion automaticamente (conexion.close())
    }
    private void ejecutaRegistro(String nombre, String descripcion, Integer cantidad, PreparedStatement preparedStatement, Map<String, String> producto) throws SQLException{
        preparedStatement.setString(1, nombre);
        preparedStatement.setString(2, descripcion);
        preparedStatement.setInt(3, cantidad);
        System.out.println("[ProductoController] Query Prepared Guardar:" + preparedStatement);

        // error de ejemplo, para rpbar el rollback
        // if (cantidad < 50) {
        //     throw new RuntimeException("Error de ejemplo");
        // }

        preparedStatement.execute();

        final ResultSet resultSet = preparedStatement.getGeneratedKeys();
        try(resultSet){
            while(resultSet.next()){
                producto.put("ID", resultSet.getString(1));
                System.out.println("[ProductoController] ID:" + producto.get("ID"));
            }
        }
        
        
    }

}
