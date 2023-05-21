package com.example.fesc.sigeliFesc.models.respuestas;

import com.example.fesc.sigeliFesc.shared.PrestamoDto;

import java.sql.Date;

public class MultaDataRestModel {

    private long id;
    private PrestamoDataRestModel prestamoEntity;
    private Date fechaPago;
    private  String estado;
    private int pago;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PrestamoDataRestModel getPrestamoEntity() {
        return prestamoEntity;
    }

    public void setPrestamoEntity(PrestamoDataRestModel prestamoEntity) {
        this.prestamoEntity = prestamoEntity;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getPago() {
        return pago;
    }

    public void setPago(int pago) {
        this.pago = pago;
    }
}
