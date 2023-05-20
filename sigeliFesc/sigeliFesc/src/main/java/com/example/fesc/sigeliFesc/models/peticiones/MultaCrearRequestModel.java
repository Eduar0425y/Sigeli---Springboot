package com.example.fesc.sigeliFesc.models.peticiones;

import com.example.fesc.sigeliFesc.data.entidades.PrestamoEntity;

import java.sql.Date;

public class MultaCrearRequestModel {

    private long idPrestamo;

    public long getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(long idPrestamo) {
        this.idPrestamo = idPrestamo;
    }
}
