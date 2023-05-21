package com.example.fesc.sigeliFesc.controllers;

import com.example.fesc.sigeliFesc.models.peticiones.CategoriaCrearRequestModel;
import com.example.fesc.sigeliFesc.models.peticiones.CategoriaLibroCrearRequestModel;
import com.example.fesc.sigeliFesc.models.respuestas.CategoriaDataRestModel;
import com.example.fesc.sigeliFesc.models.respuestas.CategoriaLibroDataRestModel;
import com.example.fesc.sigeliFesc.services.ICategoriaLibroService;
import com.example.fesc.sigeliFesc.shared.CategoriaDto;
import com.example.fesc.sigeliFesc.shared.CategoriaLibroDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categoriaLibro")
public class CategoriaLibroController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ICategoriaLibroService iCategoriaLibroService;

    @GetMapping(path = "/isbnLibro/{isbn}")
    public List<CategoriaLibroDataRestModel> verCategoriasLibro(@PathVariable("isbn")String isbn){

        List<CategoriaLibroDataRestModel> categoriaLibroDataRestModelList = new ArrayList<>();

        List<CategoriaLibroDto> categoriaLibroDtoList = iCategoriaLibroService.verCategoriaLibro(isbn);

        for(CategoriaLibroDto categoriaLibroDto : categoriaLibroDtoList){

            CategoriaLibroDataRestModel categoriaLibroDataRestModel = modelMapper.map(categoriaLibroDto, CategoriaLibroDataRestModel.class);

            categoriaLibroDataRestModelList.add(categoriaLibroDataRestModel);

        }

        return categoriaLibroDataRestModelList;

    }

    @GetMapping(path = "/categorias/{id}")
    public List<CategoriaLibroDataRestModel> verCategoriasLibro(@PathVariable("id")int id){

        List<CategoriaLibroDataRestModel> categoriaLibroDataRestModelList = new ArrayList<>();

        List<CategoriaLibroDto> categoriaLibroDtoList = iCategoriaLibroService.verLibrosCategoria(id);

        for(CategoriaLibroDto categoriaLibroDto : categoriaLibroDtoList){

            CategoriaLibroDataRestModel categoriaLibroDataRestModel = modelMapper.map(categoriaLibroDto, CategoriaLibroDataRestModel.class);

            categoriaLibroDataRestModelList.add(categoriaLibroDataRestModel);

        }

        return categoriaLibroDataRestModelList;

    }

    @PostMapping()
    public CategoriaLibroDataRestModel crearCategoriaLibro(@RequestBody CategoriaLibroCrearRequestModel categoriaLibroCrearRequestModel){

        CategoriaLibroDto categoriaLibroDto = modelMapper.map(categoriaLibroCrearRequestModel, CategoriaLibroDto.class);

        CategoriaLibroDto categoriaLibroDtoRes = iCategoriaLibroService.crearCategoriaLibro(categoriaLibroDto);

        CategoriaLibroDataRestModel categoriaLibroDataRestModelRes = modelMapper.map(categoriaLibroDtoRes, CategoriaLibroDataRestModel.class);

        return categoriaLibroDataRestModelRes;

    }

    @PostMapping(path = "/eliminar")
    public String eliminarCategoriaLibro(@RequestBody CategoriaLibroCrearRequestModel categoriaLibroCrearRequestModel){

        CategoriaLibroDto categoriaLibroDto = modelMapper.map(categoriaLibroCrearRequestModel, CategoriaLibroDto.class);

        String respuesta = iCategoriaLibroService.eliminarCategoriaLibro(categoriaLibroDto);

        return respuesta;

    }




}
