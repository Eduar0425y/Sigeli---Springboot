package com.example.fesc.sigeliFesc.data.entidades;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Date;

@Entity(name = "prestamo")
public class PrestamoEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "idPersona", referencedColumnName = "documento")
    private UsuarioEntity usuarioEntity;

    @ManyToOne
    @JoinColumn(name = "libroIsbn",referencedColumnName = "ISBN")
    private LibroEntity libroEntity;

    private Date fechaPrestamo;

    private Date fechaEntrega;

    private int idEstado;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UsuarioEntity getUsuarioEntity() {
        return usuarioEntity;
    }

    public void setUsuarioEntity(UsuarioEntity usuarioEntity) {
        this.usuarioEntity = usuarioEntity;
    }

    public LibroEntity getLibroEntity() {
        return libroEntity;
    }

    public void setLibroEntity(LibroEntity libroEntity) {
        this.libroEntity = libroEntity;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }
}
