package com.example.fesc.sigeliFesc.services;

import com.example.fesc.sigeliFesc.data.entidades.LibroEntity;
import com.example.fesc.sigeliFesc.data.entidades.PrestamoEntity;
import com.example.fesc.sigeliFesc.data.entidades.UsuarioEntity;
import com.example.fesc.sigeliFesc.data.repositorios.ILibroRepository;
import com.example.fesc.sigeliFesc.data.repositorios.IPrestamoRepository;
import com.example.fesc.sigeliFesc.data.repositorios.IUsuarioRepository;
import com.example.fesc.sigeliFesc.models.clasesEnum.estados;
import com.example.fesc.sigeliFesc.shared.PrestamoDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrestamoService implements IPrestamoService{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IPrestamoRepository iPrestamoRepository;

    @Autowired
    ILibroRepository iLibroRepository;

    @Autowired
    IUsuarioRepository iUsuarioRepository;



    @Override
    public List<PrestamoDto> verPrestamos() {

        PrestamoDto prestamoDto;
        List<PrestamoDto> prestamoDtoList = new ArrayList<>();

        for(PrestamoEntity prestamoEntity : iPrestamoRepository.findAll()){

            if(prestamoEntity.getIdEstado() == 3){

                prestamoDto = modelMapper.map(prestamoEntity, PrestamoDto.class);
                prestamoDto.setEstado(String.valueOf(estados.en_Deuda));
                prestamoDtoList.add(prestamoDto);

            }

        }

        return prestamoDtoList;

    }

    @Override
    public List<PrestamoDto> verPrestamosHoy() {

        PrestamoDto prestamoDto;
        List<PrestamoDto> prestamoDtoList = new ArrayList<>();

        for(PrestamoEntity prestamoEntity : iPrestamoRepository.findByIdEstado(3)){

            if(prestamoEntity.getFechaEntrega().equals(Date.valueOf(LocalDate.now()))){

                prestamoDto = modelMapper.map(prestamoEntity, PrestamoDto.class);
                prestamoDto.setEstado(String.valueOf(estados.en_Deuda));
                prestamoDtoList.add(prestamoDto);

            }

        }

        return prestamoDtoList;

    }

    @Override
    public List<PrestamoDto> buscarPrestamosPersona(String documento) {

        PrestamoDto prestamoDto;
        List<PrestamoDto> prestamoDtoList = new ArrayList<>();

        for(PrestamoEntity prestamoEntity : iPrestamoRepository.findByUsuarioEntity(iUsuarioRepository.findByDocumento(documento))){

            if(prestamoEntity.getIdEstado() == 3){

                prestamoDto = modelMapper.map(prestamoEntity, PrestamoDto.class);
                prestamoDto.setEstado(String.valueOf(estados.en_Deuda));
                prestamoDtoList.add(prestamoDto);

            }

        }

        return prestamoDtoList;


    }

    @Override
    public List<PrestamoDto> buscarPrestamosLibro(String isbn) {

        List<PrestamoDto> prestamoDtoList = new ArrayList<>();

        for(PrestamoEntity prestamoEntity : iPrestamoRepository.findByLibroEntity(iLibroRepository.findByIsbn(isbn))){

            if (prestamoEntity.getIdEstado() == 3) {

                PrestamoDto prestamoDto = modelMapper.map(prestamoEntity, PrestamoDto.class);
                prestamoDto.setEstado(String.valueOf(estados.en_Deuda));

                prestamoDtoList.add(prestamoDto);

            }
        }

        return prestamoDtoList;


    }

    @Override
    public PrestamoDto crearPrestamo(PrestamoDto prestamoDto) {

        UsuarioEntity usuarioEntity = iUsuarioRepository.findByDocumento(prestamoDto.getDocumentoPersona());
        LibroEntity libroEntity = iLibroRepository.findByIsbn(prestamoDto.getIsbnLibro());

        if(iPrestamoRepository.findByLibroEntity(libroEntity) != null){

            for(PrestamoEntity prestamoEntity : iPrestamoRepository.findByLibroEntity(libroEntity)){

                if(prestamoEntity.getIdEstado() == 3){

                    throw new RuntimeException("Este libro ya fue prestado");

                }
            }

        }

        if(iPrestamoRepository.findByUsuarioEntity(usuarioEntity) != null){

            for(PrestamoEntity prestamoEntity : iPrestamoRepository.findByUsuarioEntity(usuarioEntity)){

                if(prestamoEntity.getIdEstado() == 3){

                    throw new RuntimeException("Este usuario ya tiene un prestamo");

                }
            }

        }

        if(prestamoDto.getFechaEntrega().before(Date.valueOf(LocalDate.now()))){

            throw new RuntimeException("No se puede poner una fecha de entrega menor que la fecha de hoy");

        }

        if(usuarioEntity.getEstadoCuenta() == 1){

            PrestamoEntity prestamoEntity = modelMapper.map(prestamoDto, PrestamoEntity.class);

            prestamoEntity.setUsuarioEntity(usuarioEntity);
            prestamoEntity.setLibroEntity(libroEntity);
            prestamoEntity.setFechaPrestamo(Date.valueOf(LocalDate.now()));
            prestamoEntity.setIdEstado(3);

            PrestamoEntity prestamoEntityRest = iPrestamoRepository.save(prestamoEntity);

            PrestamoDto prestamoDtoRest = modelMapper.map(prestamoEntityRest, PrestamoDto.class);

            return prestamoDtoRest;

        }
        else{
            throw new RuntimeException("No puede hacer un prestamo a un usuario eliminado de la BD");
        }



    }

    @Override
    public String entregaPrestamo(long idPrestamo) {

        PrestamoEntity prestamoEntity = iPrestamoRepository.findById(idPrestamo);

        if(prestamoEntity.getIdEstado() != 2){

            prestamoEntity.setIdEstado(2);

            iPrestamoRepository.save(prestamoEntity);

            return "Prestamo entregado con éxito";

        }
        else{

            return "No se ha podido hacer la entrega";

        }

    }

}
