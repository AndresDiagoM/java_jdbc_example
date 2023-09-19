package com.alura.jdbc.DAO;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;
import com.mysql.cj.jdbc.PreparedStatementWrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public List<Categoria> listarConProductos(){
        List<Categoria> categorias = new ArrayList<Categoria>();
        
        try {
            final PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM categoria c INNER JOIN producto p ON c.id = p.categoria_id");
            try(stmt){
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {

                    int categoria_id = rs.getInt("c.id");
                    String categoria_nombre = rs.getString("c.nombre");

                    var categoria = categorias.stream()
                        .filter(cat -> cat.getId().equals(categoria_id))
                        .findAny().orElseGet(()->{
                            var cat = new Categoria(categoria_id, categoria_nombre);
                            categorias.add(cat);
                            return cat;
                        });

                    var producto = new Producto(rs.getInt("p.id"), 
                                    rs.getString("p.nombre"), 
                                    rs.getString("p.descripcion"), 
                                    rs.getInt("p.cantidad"), 
                                    categoria.getId());

                    categoria.addProducto(producto);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("\n[CategoriaDAO] Listado de categorias: " + categorias + "\n");
        return categorias;
    }
}
