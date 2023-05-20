package com.example.fesc.sigeliFesc.models.respuestas;

import com.example.fesc.sigeliFesc.data.entidades.CategoriaEntity;
import com.example.fesc.sigeliFesc.data.entidades.LibroEntity;

public class CategoriaLibroDataRestModel {

    private long id;
    private CategoriaDataRestModel categoriaEntity;
    private LibroDataRestModel libroEntity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CategoriaDataRestModel getCategoriaEntity() {
        return categoriaEntity;
    }

    public void setCategoriaEntity(CategoriaDataRestModel categoriaEntity) {
        this.categoriaEntity = categoriaEntity;
    }

    public LibroDataRestModel getLibroEntity() {
        return libroEntity;
    }

    public void setLibroEntity(LibroDataRestModel libroEntity) {
        this.libroEntity = libroEntity;
    }
}
