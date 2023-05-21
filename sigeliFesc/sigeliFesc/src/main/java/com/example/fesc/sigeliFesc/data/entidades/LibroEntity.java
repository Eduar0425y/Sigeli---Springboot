package com.example.fesc.sigeliFesc.data.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity(name = "libro")
public class LibroEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private int edicion;

    @Column(nullable = false)
    private int creacion;

    @Column(nullable = false)
    private String estante;

    @Column(nullable = false)
    private String fila;

    @Column(nullable = false)
    private int estado;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getEdicion() {
        return edicion;
    }

    public void setEdicion(int edicion) {
        this.edicion = edicion;
    }

    public int getCreacion() {
        return creacion;
    }

    public void setCreacion(int creacion) {
        this.creacion = creacion;
    }

    public String getEstante() {
        return estante;
    }

    public void setEstante(String estante) {
        this.estante = estante;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
