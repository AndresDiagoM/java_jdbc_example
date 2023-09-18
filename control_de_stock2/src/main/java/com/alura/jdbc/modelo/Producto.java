package com.alura.jdbc.modelo;


public class Producto {
    
    //---------------- ATRIBUTOS ----------------/
    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer cantidad;

    //---------------- CONSTRUCTOR ----------------/
    public Producto(Integer id, String nombre, String descripcion, Integer cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }
    public Producto(){
        this(null, null, null, null);
    }

    //---------------- METODOS ----------------
    @Override
    public String toString() {
        return "Producto [cantidad=" + cantidad + ", descripcion=" + descripcion + ", id=" + id + ", nombre=" + nombre
                + "]";
    }



    //---------------- GETTERS Y SETTERS ----------------/
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        if(nombre == null || nombre.isEmpty()){
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacio");
        }
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        if(descripcion == null || descripcion.isEmpty()){
            throw new IllegalArgumentException("La descripcion no puede ser nula o vacia");
        }
        this.descripcion = descripcion;
    }

    public void setCantidad(Integer cantidad) {
        if(cantidad == null || cantidad < 0){
            throw new IllegalArgumentException("La cantidad no puede ser nula o negativa");
        }
        this.cantidad = cantidad;
    }
}
