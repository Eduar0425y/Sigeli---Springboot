package com.example.fesc.sigeliFesc.controllers;

import com.example.fesc.sigeliFesc.models.peticiones.CategoriaCrearRequestModel;
import com.example.fesc.sigeliFesc.models.respuestas.CategoriaDataRestModel;
import com.example.fesc.sigeliFesc.services.ICategoriaService;
import com.example.fesc.sigeliFesc.shared.CategoriaDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ICategoriaService iCategoriaService;

    @GetMapping()
    public List<CategoriaDataRestModel> verCategorias(){

        List<CategoriaDataRestModel> categoriaDataRestModelList = new ArrayList<>();

        for(CategoriaDto categoriaDto : iCategoriaService.verCategorias()){

            CategoriaDataRestModel categoriaDataRestModel = new CategoriaDataRestModel();

            categoriaDataRestModel = modelMapper.map(categoriaDto, CategoriaDataRestModel.class);

            categoriaDataRestModelList.add(categoriaDataRestModel);

        }

        return categoriaDataRestModelList;

    }


    @GetMapping(path = "/buscar/{nombre}")
    public CategoriaDataRestModel buscarCategoria(@PathVariable("nombre")String nombre){

        CategoriaDto categoriaDto = iCategoriaService.buscarCategoria(nombre);

        CategoriaDataRestModel categoriaDataRestModel = modelMapper.map(categoriaDto, CategoriaDataRestModel.class);

        return categoriaDataRestModel;

    }

    @PostMapping("/actualizar")
    public CategoriaDataRestModel editarCategoria(@RequestBody CategoriaCrearRequestModel categoriaCrearRequestModel){

        CategoriaDto categoriaDto = iCategoriaService.editarCategoria(modelMapper.map(categoriaCrearRequestModel, CategoriaDto.class));

        CategoriaDataRestModel categoriaDataRestModelReturn = modelMapper.map(categoriaDto, CategoriaDataRestModel.class);

        return categoriaDataRestModelReturn;

    }

    @PostMapping()
    public CategoriaDataRestModel crearCategoria(@RequestBody CategoriaCrearRequestModel categoriaCrearRequestModel){

        CategoriaDto categoriaDto = iCategoriaService.crearCategoria(modelMapper.map(categoriaCrearRequestModel, CategoriaDto.class));

        CategoriaDataRestModel categoriaDataRestModelReturn = modelMapper.map(categoriaDto, CategoriaDataRestModel.class);

        return categoriaDataRestModelReturn;

    }


}
