package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author felip
 */
public class ConnectionFactory {
    
    //---------------- ATRIBUTOS ----------------
    public static final String URL = "jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC";
    
    
    //---------------- METODOS ----------------
    public Connection conectar() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL,"root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[CreaConexion] Error al conectar con la base de datos");
        }
        return con;
    }
}
