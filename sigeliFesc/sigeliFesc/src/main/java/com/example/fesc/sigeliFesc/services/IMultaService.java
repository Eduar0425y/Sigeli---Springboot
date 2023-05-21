package com.example.fesc.sigeliFesc.services;


import com.example.fesc.sigeliFesc.shared.MultaDto;
import com.itextpdf.text.Document;


import java.sql.Date;
import java.util.List;

public interface IMultaService {

    public List<MultaDto> verMultas();

    public String pagarMulta(long id);

    public List<MultaDto> buscarDocumento(String documento);

    public MultaDto buscarId(long id);

    public List<MultaDto> verPagos();

    public MultaDto verPago(long id);

    public List<MultaDto> verPagoPersona(String documento);

    public Document generarReporte(int opcion, Date fechaInicio, Date fechaFinal, String observaciones);

}
