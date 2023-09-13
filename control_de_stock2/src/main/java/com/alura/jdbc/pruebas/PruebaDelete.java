package com.alura.jdbc.pruebas;

import com.alura.jdbc.factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PruebaDelete {
    
    public static void main (String[] args) throws SQLException {
        ConnectionFactory crearConexion = new ConnectionFactory();
        Connection conexion;
        // consultar si el producto existe en la base de datos
        conexion = crearConexion.conectar();
        Statement statement = conexion.createStatement();

        statement.execute( "DELETE FROM PRODUCTO WHERE ID = " + 99);

        int cantidadDeRegistrosAfectados = statement.getUpdateCount();
        System.out.println("[ProductoController] Cantidad de eliminados:" + cantidadDeRegistrosAfectados);
        
        conexion.close();
    }
}
