package com.alura.jdbc.DAO;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;
import com.mysql.cj.jdbc.PreparedStatementWrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Categoria DAO
 * 
 * @author felip
 */
public class CategoriaDAO {
    
    //---------------- ATRIBUTOS ----------------/
    private final Connection conexion;
    
    //---------------- CONSTRUCTOR ----------------/
    public CategoriaDAO(Connection con) {
        this.conexion = con;
    }
    
    //---------------- METODOS ----------------
    public List<Categoria> listarCategoria() {
        List<Categoria> categorias = new ArrayList<Categoria>();

        try {
            final PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM categoria");
            try(stmt){
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    var categoria = new Categoria(rs.getInt("id"), rs.getString("nombre"));
                    categorias.add(categoria);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("\n[CategoriaDAO] Listado de categorias: " + categorias + "\n");
        return categorias;
    }
}
