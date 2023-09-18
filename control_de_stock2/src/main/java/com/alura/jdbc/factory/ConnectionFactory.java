package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import javax.sql.DataSource;


public class ConnectionFactory {
    
    //---------------- ATRIBUTOS ----------------
    public static final String URL = "jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC";
    private DataSource dataSource;

    //---------------- CONSTRUCTOR ----------------
    public ConnectionFactory() {
        var pooledDataSource = new ComboPooledDataSource();
        pooledDataSource.setJdbcUrl(URL);
        pooledDataSource.setUser("root");
        pooledDataSource.setPassword("root");

        pooledDataSource.setMaxPoolSize(10);

        this.dataSource = pooledDataSource;
    }
    
    //---------------- METODOS ----------------
    public Connection conectar() throws SQLException {
        return this.dataSource.getConnection();
    }
}
