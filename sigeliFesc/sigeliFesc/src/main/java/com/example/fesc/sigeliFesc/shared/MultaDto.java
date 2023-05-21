package com.example.fesc.sigeliFesc.shared;

import com.example.fesc.sigeliFesc.data.entidades.PrestamoEntity;

import java.sql.Date;

public class MultaDto {


    private long id;
    private long idPrestamo;
    private PrestamoDto prestamoEntity;
    private Date fechaPago;
    private int idEstado;
    private  String estado;
    private int pago;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(long idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public PrestamoDto getPrestamoEntity() {
        return prestamoEntity;
    }

    public void setPrestamoEntity(PrestamoDto prestamoEntity) {
        this.prestamoEntity = prestamoEntity;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
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
