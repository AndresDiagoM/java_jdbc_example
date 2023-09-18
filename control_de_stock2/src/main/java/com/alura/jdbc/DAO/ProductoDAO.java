package com.alura.jdbc.DAO;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductoDAO 
 * Clase tipo DAO para persistir productos en la base de datos
 * DAO: Data Access Object, estándar de diseño de software para abstraer y encapsular el acceso a la base de datos
 * @author Andres Diago
 * @version 1.0
 * @since 2023-09-12
 */
public class ProductoDAO {
    
    //---------------- ATRIBUTOS ----------------
    private final Connection conexion;

    //---------------- CONSTRUCTOR ----------------
    public ProductoDAO(Connection con) {
        this.conexion = con;
    }

    //---------------- METODOS ----------------
    public void modificar(String nombre, String descripcion, Integer id) {
        try{
            var statement = this.conexion.prepareStatement("UPDATE PRODUCTO SET NOMBRE = ?, DESCRIPCION = ? WHERE ID = ?");
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setInt(3, id);
            statement.execute();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public int eliminar(Integer id) {
        // consultar si el producto existe en la base de datos
        try{
            var statement = this.conexion.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
            statement.setInt(1, id);
    
            int cantidadDeRegistrosAfectados = statement.getUpdateCount();
            System.out.println("[ProductoController] Cantidad de eliminados:" + cantidadDeRegistrosAfectados);
            
            return cantidadDeRegistrosAfectados;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void guardarProducto(Producto producto){
        System.out.println("[ProductoController] Guardar");
        try{
            String preparedQuery = "INSERT INTO PRODUCTO (NOMBRE, DESCRIPCION, CANTIDAD, CATEGORIA_ID)"+
                                    " VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conexion.prepareStatement(preparedQuery, Statement.RETURN_GENERATED_KEYS);
            try(preparedStatement){ // esto hace que se cierre el prepared statement automaticamente
                
                preparedStatement.setString(1, producto.getNombre());
                preparedStatement.setString(2, producto.getDescripcion());
                preparedStatement.setInt(3, producto.getCantidad());
                preparedStatement.setInt(4, producto.getCategoria_id());
                System.out.println("[ProductoController] Query Prepared Guardar:" + preparedStatement);

                preparedStatement.execute();

                final ResultSet resultSet = preparedStatement.getGeneratedKeys();

                try(resultSet){
                    while(resultSet.next()){
                        producto.setId(resultSet.getInt(1));
                        System.out.println("[ProductoController] ID:" + producto);
                    }
                }
            }
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        // esto hace que se cierre la conexion automaticamente (conexion.close())
    }

    public List<Producto> listarProducto() {
        List<Producto> productos = new ArrayList<>();
        try{
            //guardar productos en una lista
            PreparedStatement query = conexion.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD, CATEGORIA_ID FROM PRODUCTO");
            ResultSet resultSet = query.executeQuery();
            try(query){
                try(resultSet){
                    while (resultSet.next()) {
                        Producto producto = new Producto();
                        producto.setId(resultSet.getInt("ID"));
                        producto.setNombre(resultSet.getString("NOMBRE"));
                        producto.setDescripcion(resultSet.getString("DESCRIPCION"));
                        producto.setCantidad(resultSet.getInt("CANTIDAD"));
                        producto.setCategoria_id(resultSet.getInt("CATEGORIA_ID"));
                        productos.add(producto);
                    }
                }
            }
            return productos;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}