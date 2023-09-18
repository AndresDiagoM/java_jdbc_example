/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alura.jdbc.pruebas;

import com.alura.jdbc.factory.ConnectionFactory;
import java.sql.SQLException;

/**
 *
 * @author felip
 */
public class PruebaPoolConexiones {
    
    public static void main(String[] args) throws SQLException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        
        for(int i = 0; i < 20; i++){
            var connection = connectionFactory.conectar();
            System.out.println("Conexión número: " + (i+1));
        }
        // ell máximo de conexiones que se pueden tener es 10, 
        // ejecutar en mysql mientras se corre PruebaPoolConexiones -> use control_de_stock; show processlist;
    }
}
