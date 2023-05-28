package com.example.fesc.sigeliFesc.controllers;

import com.example.fesc.sigeliFesc.models.respuestas.EstadisticaDataRestModel;
import com.example.fesc.sigeliFesc.services.IEstadisticaService;
import com.example.fesc.sigeliFesc.shared.EstadisticaDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticaController {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    IEstadisticaService iEstadisticaService;

    @GetMapping
    public EstadisticaDataRestModel verEstadisticas(){

        EstadisticaDto estadisticaDto = iEstadisticaService.verEstadisticas();

        EstadisticaDataRestModel estadisticaDataRestModel = modelMapper.map(estadisticaDto, EstadisticaDataRestModel.class);

        return estadisticaDataRestModel;

    }

}
