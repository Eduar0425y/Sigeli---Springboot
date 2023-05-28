package com.example.fesc.sigeliFesc.shared;

import java.sql.Date;
import java.util.HashMap;

public class EstadisticaDto {
    private int cantPrestamos;
    private int cantPrestamosEntregados;
    private double promPrestamosEntregados;
    private int cantMultas;
    private int cantPagos;
    private double promMultasPagadas;
    private double dineroPagado;
    private double dineroEnDeuda;
    private double dineroTotal;
    private HashMap<String, Integer> porcentajesCarrerasPrestamos;

    public int getCantPrestamos() {
        return cantPrestamos;
    }

    public void setCantPrestamos(int cantPrestamos) {
        this.cantPrestamos = cantPrestamos;
    }

    public int getCantPrestamosEntregados() {
        return cantPrestamosEntregados;
    }

    public void setCantPrestamosEntregados(int cantPrestamosEntregados) {
        this.cantPrestamosEntregados = cantPrestamosEntregados;
    }

    public double getPromPrestamosEntregados() {
        return promPrestamosEntregados;
    }

    public void setPromPrestamosEntregados(double promPrestamosEntregados) {
        this.promPrestamosEntregados = promPrestamosEntregados;
    }

    public int getCantMultas() {
        return cantMultas;
    }

    public void setCantMultas(int cantMultas) {
        this.cantMultas = cantMultas;
    }

    public int getCantPagos() {
        return cantPagos;
    }

    public void setCantPagos(int cantPagos) {
        this.cantPagos = cantPagos;
    }

    public double getPromMultasPagadas() {
        return promMultasPagadas;
    }

    public void setPromMultasPagadas(double promMultasPagadas) {
        this.promMultasPagadas = promMultasPagadas;
    }

    public double getDineroPagado() {
        return dineroPagado;
    }

    public void setDineroPagado(double dineroPagado) {
        this.dineroPagado = dineroPagado;
    }

    public double getDineroEnDeuda() {
        return dineroEnDeuda;
    }

    public void setDineroEnDeuda(double dineroEnDeuda) {
        this.dineroEnDeuda = dineroEnDeuda;
    }

    public double getDineroTotal() {
        return dineroTotal;
    }

    public void setDineroTotal(double dineroTotal) {
        this.dineroTotal = dineroTotal;
    }

    public HashMap<String, Integer> getPorcentajesCarrerasPrestamos() {
        return porcentajesCarrerasPrestamos;
    }

    public void setPorcentajesCarrerasPrestamos(HashMap<String, Integer> porcentajesCarrerasPrestamos) {
        this.porcentajesCarrerasPrestamos = porcentajesCarrerasPrestamos;
    }
}
