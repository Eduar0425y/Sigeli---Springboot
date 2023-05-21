package com.example.fesc.sigeliFesc.data.entidades;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name = "categoriaLibro")
public class CategoriaLibroEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "categoriaId")
    private CategoriaEntity categoriaEntity;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "libroIsbn",referencedColumnName = "ISBN")
    private LibroEntity libroEntity;

    public CategoriaLibroEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoriaEntity getCategoriaEntity() {
        return categoriaEntity;
    }

    public void setCategoriaEntity(CategoriaEntity categoriaEntity) {
        this.categoriaEntity = categoriaEntity;
    }

    public LibroEntity getLibroEntity() {
        return libroEntity;
    }

    public void setLibroEntity(LibroEntity libroEntity) {
        this.libroEntity = libroEntity;
    }
}
