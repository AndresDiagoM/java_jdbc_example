package com.alura.jdbc.modelo;

import java.util.List;

/**
 * Categoria
 * 
 * @author felip
 */
public class Categoria {
    
    //---------------- ATRIBUTOS ----------------/
    private Integer id;
    private String nombre;
    private List<Producto> productos;

    //---------------- CONSTRUCTOR ----------------/
    public Categoria(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public Categoria(){
        this(null, null);
    }

    //---------------- METODOS ----------------
    @Override
    public String toString() {
        //return "Categoria [id=" + id + ", nombre=" + nombre + "]";
        return nombre;
    }
    public void addProducto(Producto producto){
        if(this.productos==null){
            productos = new java.util.ArrayList<>();
        }
        this.productos.add(producto);
    }

    //---------------- GETTERS Y SETTERS ----------------/
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(Integer id) {
        if(id == null || id < 0){
            throw new IllegalArgumentException("El id no puede ser nulo o negativo");
        }
        this.id = id;
    }

    public void setNombre(String nombre) {
        if(nombre == null || nombre.isEmpty()){
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacio");
        }
        this.nombre = nombre;
    }

    public List<Producto> getProductos() {
        return productos;
    }
}
