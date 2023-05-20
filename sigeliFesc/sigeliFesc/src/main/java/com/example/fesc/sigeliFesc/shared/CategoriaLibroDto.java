package com.example.fesc.sigeliFesc.shared;

import com.example.fesc.sigeliFesc.models.respuestas.CategoriaDataRestModel;
import com.example.fesc.sigeliFesc.models.respuestas.LibroDataRestModel;

public class CategoriaLibroDto {

    private int id;
    private long categoriaId;
    private String libroIsbn;
    private CategoriaDto categoriaEntity;
    private LibroDto libroEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getLibroIsbn() {
        return libroIsbn;
    }

    public void setLibroIsbn(String libroIsbn) {
        this.libroIsbn = libroIsbn;
    }

    public CategoriaDto getCategoriaEntity() {
        return categoriaEntity;
    }

    public void setCategoriaEntity(CategoriaDto categoriaEntity) {
        this.categoriaEntity = categoriaEntity;
    }

    public LibroDto getLibroEntity() {
        return libroEntity;
    }

    public void setLibroEntity(LibroDto libroEntity) {
        this.libroEntity = libroEntity;
    }
}
