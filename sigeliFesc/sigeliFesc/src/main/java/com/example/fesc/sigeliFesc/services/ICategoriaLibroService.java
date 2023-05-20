package com.example.fesc.sigeliFesc.services;

import com.example.fesc.sigeliFesc.shared.CategoriaLibroDto;

import java.util.List;

public interface ICategoriaLibroService {

    public List<CategoriaLibroDto> verCategoriaLibro(String isbn);

    public List<CategoriaLibroDto> verLibrosCategoria(int idCategoria);

    public CategoriaLibroDto crearCategoriaLibro(CategoriaLibroDto categoriaLibroDto);

    public String eliminarCategoriaLibro(CategoriaLibroDto categoriaLibroDto);


}
