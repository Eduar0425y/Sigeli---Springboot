package com.example.fesc.sigeliFesc.services;

import com.example.fesc.sigeliFesc.shared.CategoriaDto;

import java.util.List;

public interface ICategoriaService {

    public CategoriaDto crearCategoria(CategoriaDto categoriaCrearDto);

    public List<CategoriaDto> verCategorias();

    public CategoriaDto buscarCategoria(String nombre);

    public CategoriaDto editarCategoria(CategoriaDto categoriaDto);
}
