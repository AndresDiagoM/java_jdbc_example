package com.alura.tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author felip
 */
public class PruebaConeccion {
    public static void main(String[] args) throws SQLException {
        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost/control_de_stock?useTimezone=true&serverTimezone=UTC", 
            "root", "root");
        
        System.out.println("Cerrando la conexion");
        
        con.close();
    }
}
