package com.example.fesc.sigeliFesc.services;

import com.example.fesc.sigeliFesc.shared.PrestamoDto;

import java.util.List;

public interface IPrestamoService {

    public List<PrestamoDto> verPrestamos();

    public List<PrestamoDto> verPrestamosHoy();

    public List<PrestamoDto> buscarPrestamosPersona(String documento);

    public List<PrestamoDto> buscarPrestamosLibro(String isbn);

    public PrestamoDto crearPrestamo(PrestamoDto prestamoDto);

    public String entregaPrestamo(long idPrestamo);

}
