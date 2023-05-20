package com.example.fesc.sigeliFesc.models.peticiones;

public class CategoriaLibroCrearRequestModel {
    private int categoriaId;
    private String libroIsbn;

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getLibroIsbn() {
        return libroIsbn;
    }

    public void setLibroIsbn(String libroIsbn) {
        this.libroIsbn = libroIsbn;
    }
}
