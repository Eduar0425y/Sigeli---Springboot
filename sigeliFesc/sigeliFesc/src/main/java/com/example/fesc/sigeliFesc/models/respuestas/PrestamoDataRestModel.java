package com.example.fesc.sigeliFesc.models.respuestas;

import com.example.fesc.sigeliFesc.data.entidades.LibroEntity;
import com.example.fesc.sigeliFesc.data.entidades.UsuarioEntity;

import java.sql.Date;

public class PrestamoDataRestModel {

    private long id;
    private Date fechaEntrega;
    private  Date fechaPrestamo;
    private LibroDataRestModel libroEntity;
    private UsuarioDataRestModel usuarioEntity;
    private String estado;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LibroDataRestModel getLibroEntity() {
        return libroEntity;
    }

    public void setLibroEntity(LibroDataRestModel libroEntity) {
        this.libroEntity = libroEntity;
    }

    public UsuarioDataRestModel getUsuarioEntity() {
        return usuarioEntity;
    }

    public void setUsuarioEntity(UsuarioDataRestModel usuarioEntity) {
        this.usuarioEntity = usuarioEntity;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
