package com.alura.jdbc.controller;

import com.alura.jdbc.DAO.CategoriaDAO;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;

public class CategoriaController {

    //---------------- ATRIBUTOS ----------------
    CategoriaDAO CategoriaDAO;

    //---------------- CONSTRUCTOR ----------------
    public CategoriaController() {
        this.CategoriaDAO = new CategoriaDAO(new ConnectionFactory().conectar());
    }

	public List<Categoria> listar() {
		return CategoriaDAO.listarCategoria();
	}

    public List<Categoria> cargaReporte() {
        return this.listar();
    }

}
