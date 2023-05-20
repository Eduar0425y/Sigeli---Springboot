package com.example.fesc.sigeliFesc.controllers;

import com.example.fesc.sigeliFesc.models.peticiones.LibroCrearRequestModel;
import com.example.fesc.sigeliFesc.models.respuestas.LibroDataRestModel;
import com.example.fesc.sigeliFesc.services.ILibroService;
import com.example.fesc.sigeliFesc.shared.LibroDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Libro")
public class LibroController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ILibroService iLibroService;

    @GetMapping()
    public List<LibroDataRestModel> verLibros(){

        List<LibroDataRestModel> libroDataRestModelsList = new ArrayList<>();

        for(LibroDto libroDto : iLibroService.verLibros()){

            LibroDataRestModel libroDataRestModel = modelMapper.map(libroDto, LibroDataRestModel.class);

            libroDataRestModelsList.add(libroDataRestModel);

        }

        return libroDataRestModelsList;

    }

    @GetMapping(path = "/buscarModo/{palabra}/{modo}")
    public List<LibroDataRestModel> buscarModo(@PathVariable("palabra") String palabra, @PathVariable("modo") int modo){

        List<LibroDataRestModel> libroDataRestModelsList = new ArrayList<>();

        for(LibroDto libroDto : iLibroService.bucarLibroModo(palabra, modo)){

            LibroDataRestModel libroDataRestModel = modelMapper.map(libroDto, LibroDataRestModel.class);

            libroDataRestModelsList.add(libroDataRestModel);

        }

        return libroDataRestModelsList;

    }

    @GetMapping(path = "/buscar/{palabra}")
    public List<LibroDataRestModel> buscar(@PathVariable("palabra") String palabra){

        List<LibroDataRestModel> libroDataRestModelsList = new ArrayList<>();

        for(LibroDto libroDto : iLibroService.bucarLibro(palabra)){

            LibroDataRestModel libroDataRestModel = modelMapper.map(libroDto, LibroDataRestModel.class);

            libroDataRestModelsList.add(libroDataRestModel);

        }

        return libroDataRestModelsList;

    }

    @PostMapping()
    public LibroDataRestModel crearLibro(@RequestBody LibroCrearRequestModel libroCrearRequestModel){

        LibroDto libroDto = modelMapper.map(libroCrearRequestModel, LibroDto.class);

        LibroDto libroDtoRest = iLibroService.crearLibro(libroDto);

        LibroDataRestModel libroDataRestModelRest = modelMapper.map(libroDtoRest, LibroDataRestModel.class);

        return libroDataRestModelRest;

    }

    @PostMapping(path = "/actualizar")
    public LibroDataRestModel actulizar(@RequestBody LibroCrearRequestModel libroCrearRequestModel){

        LibroDto libroDto = modelMapper.map(libroCrearRequestModel, LibroDto.class);

        LibroDto libroDtoRest = iLibroService.actualizarLibro(libroDto);

        LibroDataRestModel libroDataRestModelRest = modelMapper.map(libroDtoRest, LibroDataRestModel.class);

        return libroDataRestModelRest;

    }

    @PostMapping(path = "/estado/{isbn}/{estado}")
    public String cambiarEstado(@PathVariable("isbn") String isbn, @PathVariable("estado") int estado){

        String respuesta = iLibroService.cambiarEstado(isbn, estado);

        return respuesta;

    }

    @PostMapping(path = "/eliminar/{isbn}")
    public String eliminarLibro(@PathVariable("isbn") String isbn){

        String respuesta = iLibroService.cambiarEstado(isbn, 1);

        return respuesta;

    }

}
