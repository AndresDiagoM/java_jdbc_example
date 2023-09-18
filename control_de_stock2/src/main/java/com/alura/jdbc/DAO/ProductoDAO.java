package com.alura.jdbc.DAO;

import com.alura.jdbc.factory.ConnectionFactory;
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
    public ProductoDAO() {
        this.conexion = new ConnectionFactory().conectar();
    }

    //---------------- METODOS ----------------
    public void modificar(String nombre, String descripcion, Integer id) {
        try(this.conexion){
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
        try(this.conexion){
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
        
        try(conexion){
            String preparedQuery = "INSERT INTO PRODUCTO (NOMBRE, DESCRIPCION, CANTIDAD) VALUES (?, ?, ?)";
            final PreparedStatement preparedStatement = conexion.prepareStatement(preparedQuery, Statement.RETURN_GENERATED_KEYS);

            try(preparedStatement){ // esto hace que se cierre el prepared statement automaticamente
                ejecutaRegistro(preparedStatement, producto);
            } 
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        // esto hace que se cierre la conexion automaticamente (conexion.close())
    }
    private void ejecutaRegistro(PreparedStatement preparedStatement, Producto producto) throws SQLException{
        preparedStatement.setString(1, producto.getNombre());
        preparedStatement.setString(2, producto.getDescripcion());
        preparedStatement.setInt(3, producto.getCantidad());
        System.out.println("[ProductoController] Query Prepared Guardar:" + preparedStatement);

        // error de ejemplo, para rpbar el rollback
        // if (cantidad < 50) {
        //     throw new RuntimeException("Error de ejemplo");
        // }

        preparedStatement.execute();

        final ResultSet resultSet = preparedStatement.getGeneratedKeys();
        try(resultSet){
            while(resultSet.next()){
                producto.setId(resultSet.getInt(1));
                System.out.println("[ProductoController] ID:" + producto);
            }
        }
    }

    public List<Producto> listarProducto() {
        List<Producto> productos = new ArrayList<>();
        try(this.conexion){
            //guardar productos en una lista
            PreparedStatement query = conexion.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
            ResultSet resultSet = query.executeQuery();
            try(query){
                try(resultSet){
                    while (resultSet.next()) {
                        Producto producto = new Producto();
                        producto.setId(resultSet.getInt("ID"));
                        producto.setNombre(resultSet.getString("NOMBRE"));
                        producto.setDescripcion(resultSet.getString("DESCRIPCION"));
                        producto.setCantidad(resultSet.getInt("CANTIDAD"));
                        productos.add(producto);
                    }
                }
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return productos;
    }


}
