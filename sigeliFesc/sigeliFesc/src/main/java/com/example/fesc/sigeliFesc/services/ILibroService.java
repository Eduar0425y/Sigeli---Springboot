package com.example.fesc.sigeliFesc.services;

import com.example.fesc.sigeliFesc.shared.LibroDto;

import java.util.List;

public interface ILibroService {

    public List<LibroDto> verLibros();

    public LibroDto crearLibro(LibroDto libroDto);

    public List<LibroDto> bucarLibroModo(String palabra, int modo);

    public List<LibroDto> bucarLibro(String palabra);

    public LibroDto actualizarLibro(LibroDto libroDto);

    public String cambiarEstado(String isbn, int estado);



}
