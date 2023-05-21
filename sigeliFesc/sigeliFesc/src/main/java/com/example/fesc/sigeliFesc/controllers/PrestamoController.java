package com.example.fesc.sigeliFesc.controllers;

import com.example.fesc.sigeliFesc.models.peticiones.PrestamoCrearRequestModel;
import com.example.fesc.sigeliFesc.models.respuestas.PrestamoDataRestModel;
import com.example.fesc.sigeliFesc.services.IPrestamoService;
import com.example.fesc.sigeliFesc.shared.PrestamoDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/prestamo")
public class PrestamoController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IPrestamoService iPrestamoService;

    @GetMapping
    public List<PrestamoDataRestModel> verPrestamos(){

        List<PrestamoDataRestModel> prestamoDataRestModelList = new ArrayList<>();

        PrestamoDataRestModel prestamoDataRestModel;

        for(PrestamoDto prestamoDto: iPrestamoService.verPrestamos()){

            prestamoDataRestModel = modelMapper.map(prestamoDto, PrestamoDataRestModel.class);
            prestamoDataRestModelList.add(prestamoDataRestModel);

        }

        return prestamoDataRestModelList;

    }

    @GetMapping(path = "/hoy")
    public List<PrestamoDataRestModel> verPrestamosHoy(){

        List<PrestamoDataRestModel> prestamoDataRestModelList = new ArrayList<>();

        PrestamoDataRestModel prestamoDataRestModel;

        for(PrestamoDto prestamoDto: iPrestamoService.verPrestamosHoy()){

            prestamoDataRestModel = modelMapper.map(prestamoDto, PrestamoDataRestModel.class);
            prestamoDataRestModelList.add(prestamoDataRestModel);

        }

        return prestamoDataRestModelList;

    }

    @GetMapping(path = "/buscar/{documento}")
    public List<PrestamoDataRestModel> buscarDocumento(@PathVariable("documento") String documento){

        List<PrestamoDataRestModel> prestamoDataRestModelList = new ArrayList<>();

        PrestamoDataRestModel prestamoDataRestModel;

        for(PrestamoDto prestamoDto: iPrestamoService.buscarPrestamosPersona(documento)){

            prestamoDataRestModel = modelMapper.map(prestamoDto, PrestamoDataRestModel.class);
            prestamoDataRestModelList.add(prestamoDataRestModel);

        }

        return prestamoDataRestModelList;

    }

    @GetMapping(path = "/buscar/libro/{isbn}")
    public List<PrestamoDataRestModel> buscarIsbn(@PathVariable("isbn") String isbn){

        List<PrestamoDataRestModel> prestamoDataRestModelList = new ArrayList<>();

        PrestamoDataRestModel prestamoDataRestModel;

        for(PrestamoDto prestamoDto: iPrestamoService.buscarPrestamosLibro(isbn)){

            prestamoDataRestModel = modelMapper.map(prestamoDto, PrestamoDataRestModel.class);
            prestamoDataRestModelList.add(prestamoDataRestModel);

        }

        return prestamoDataRestModelList;


    }

    @PostMapping
    public PrestamoDataRestModel crearPrestamo(@RequestBody PrestamoCrearRequestModel prestamoCrearRequestModel){

        PrestamoDto prestamoDto = modelMapper.map(prestamoCrearRequestModel, PrestamoDto.class);

        PrestamoDto prestamoDtoRest = iPrestamoService.crearPrestamo(prestamoDto);

        PrestamoDataRestModel prestamoDataRestModel = modelMapper.map(prestamoDtoRest, PrestamoDataRestModel.class);

        return prestamoDataRestModel;

    }

    @PostMapping("/entregar/{id}")
    public String entregarLibro(@PathVariable("id") long id){

        String respuesta = iPrestamoService.entregaPrestamo(id);

        return respuesta;

    }

}
