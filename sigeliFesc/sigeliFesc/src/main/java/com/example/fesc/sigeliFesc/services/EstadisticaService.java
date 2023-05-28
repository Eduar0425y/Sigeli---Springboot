package com.example.fesc.sigeliFesc.services;

import com.example.fesc.sigeliFesc.data.entidades.MultaEntity;
import com.example.fesc.sigeliFesc.data.entidades.PrestamoEntity;
import com.example.fesc.sigeliFesc.data.entidades.UsuarioEntity;
import com.example.fesc.sigeliFesc.data.repositorios.IMultaRepository;
import com.example.fesc.sigeliFesc.data.repositorios.IPrestamoRepository;
import com.example.fesc.sigeliFesc.data.repositorios.IUsuarioRepository;
import com.example.fesc.sigeliFesc.models.clasesEnum.carrera;
import com.example.fesc.sigeliFesc.shared.EstadisticaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class EstadisticaService implements IEstadisticaService{

    @Autowired
    IMultaRepository iMultaRepository;

    @Autowired
    IPrestamoRepository iPrestamoRepository;

    @Autowired
    IMultaService iMultaService;


    @Override
    public EstadisticaDto verEstadisticas() {

        iMultaService.verMultas();

        List<MultaEntity> listaMultas = iMultaRepository.findByIdEstado(6);

        List<MultaEntity> listaPagos = iMultaRepository.findByIdEstado(5);

        List<PrestamoEntity> listaPrestamos = iPrestamoRepository.findByIdEstado(3);

        List<PrestamoEntity> listaPrestamosEntregados = iPrestamoRepository.findByIdEstado(2);

        HashMap<String, Integer> porcentajesCarrerasPrestamos = new HashMap<>();


        int prestamos = listaPrestamos.size();

        int prestamosEntregados = listaPrestamosEntregados.size();

        double promPrestamos = prestamosEntregados / prestamos;

        int multas = listaMultas.size();

        int cantPagos = listaPagos.size();

        double promMultasPagadas = cantPagos / (cantPagos+multas);

        int dineroPagado = 0, dineroEnDeuda = 0;


        for(MultaEntity multaEntity : listaMultas){

            dineroEnDeuda += multaEntity.getPago();

        }

        for(MultaEntity multaEntity : listaPagos){

            dineroPagado += multaEntity.getPago();

        }

        int prestamosCarrera = 0;

        for(int i = 0; i < 6; i++){
            for(PrestamoEntity prestamoEntity : listaPrestamos){
                if(prestamoEntity.getUsuarioEntity().getIdCarrera() == i){
                    prestamosCarrera ++;
                }
            }

            porcentajesCarrerasPrestamos.put(carrera.ordinal(i), (prestamosCarrera/prestamos) * 100);
            prestamosCarrera = 0;
        }

        EstadisticaDto estadisticaDto = new EstadisticaDto();
        estadisticaDto.setCantPrestamos(prestamos);
        estadisticaDto.setCantPrestamosEntregados(prestamosEntregados);
        estadisticaDto.setPromPrestamosEntregados(promPrestamos);
        estadisticaDto.setCantMultas(multas);
        estadisticaDto.setCantPagos(cantPagos);
        estadisticaDto.setPromMultasPagadas(promMultasPagadas);
        estadisticaDto.setDineroPagado(dineroPagado);
        estadisticaDto.setDineroEnDeuda(dineroEnDeuda);
        estadisticaDto.setDineroTotal(dineroPagado + dineroEnDeuda);
        estadisticaDto.setPorcentajesCarrerasPrestamos(porcentajesCarrerasPrestamos);

        return estadisticaDto;

    }
}
