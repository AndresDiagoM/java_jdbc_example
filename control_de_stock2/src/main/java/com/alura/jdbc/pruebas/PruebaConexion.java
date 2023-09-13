package com.alura.jdbc.pruebas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.alura.jdbc.factory.ConnectionFactory;

public class PruebaConexion {

    public static void main(String[] args) throws SQLException {
        // Connection con = DriverManager.getConnection(
        //         "jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC",
        //         "root",
        //         "root");
        ConnectionFactory crearConexion = new ConnectionFactory();
        Connection con = crearConexion.conectar();

        System.out.println("Cerrando la conexion");

        con.close();
    }

}
