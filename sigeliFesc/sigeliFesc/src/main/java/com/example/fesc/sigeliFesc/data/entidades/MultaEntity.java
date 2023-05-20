package com.example.fesc.sigeliFesc.data.entidades;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;

@Entity(name = "multa")
public class MultaEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "idPrestamo", referencedColumnName = "id")
    private PrestamoEntity prestamoEntity;

    @Column(name = "fechaPago")
    private Date fechaPago;

    @Column(name = "idEstado")
    private int idEstado;

    @Column(name = "pago")
    private int pago;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PrestamoEntity getPrestamoEntity() {
        return prestamoEntity;
    }

    public void setPrestamoEntity(PrestamoEntity prestamoEntity) {
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

    public int getPago() {
        return pago;
    }

    public void setPago(int pago) {
        this.pago = pago;
    }
}
