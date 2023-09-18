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

    public ProductoDAO() throws SQLException {
        this.conexion = new ConnectionFactory().conectar();
    }

    //---------------- METODOS ----------------
    public void modificar(String nombre, String descripcion, Integer id) throws SQLException {
        try(this.conexion){
            var statement = this.conexion.prepareStatement("UPDATE PRODUCTO SET NOMBRE = ?, DESCRIPCION = ? WHERE ID = ?");
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setInt(3, id);
            statement.execute();
        }
    }

    public int eliminar(Integer id) throws SQLException {
        // consultar si el producto existe en la base de datos
        try(this.conexion){
            var statement = this.conexion.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
            statement.setInt(1, id);
    
            int cantidadDeRegistrosAfectados = statement.getUpdateCount();
            System.out.println("[ProductoController] Cantidad de eliminados:" + cantidadDeRegistrosAfectados);
            
            return cantidadDeRegistrosAfectados;
        }
    }

    public void guardarProducto(Producto producto) throws SQLException{
        
        try(conexion){
            conexion.setAutoCommit(false); //para que no se haga commit automatico
            //evitar inyeccion sql con prepared statement
            String preparedQuery = "INSERT INTO PRODUCTO (NOMBRE, DESCRIPCION, CANTIDAD) VALUES (?, ?, ?)";
            final PreparedStatement preparedStatement = conexion.prepareStatement(preparedQuery, Statement.RETURN_GENERATED_KEYS);

            try(preparedStatement){ // esto hace que se cierre el prepared statement automaticamente
                ejecutaRegistro(preparedStatement, producto);                
                conexion.commit(); //hacer commit manual en base de datos
                System.out.println("[ProductoController] Commit exitodo JDBC:" + preparedStatement);
            } catch (Exception e) {
                conexion.rollback(); //hacer rollback manual en base de datos
                //un rollback es para que no se haga ningun cambio en la base de datos
                System.out.println("[ProductoController] Commit fallido JDBC:" + preparedStatement);
                throw new RuntimeException(e);
            }
        } // esto hace que se cierre la conexion automaticamente (conexion.close())
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

    public List<Producto> listarProducto() throws SQLException{
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
        }
        return productos;
    }


}
