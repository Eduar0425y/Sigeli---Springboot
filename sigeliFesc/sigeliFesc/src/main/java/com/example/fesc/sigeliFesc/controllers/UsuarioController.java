package com.example.fesc.sigeliFesc.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.fesc.sigeliFesc.models.peticiones.UsuarioCrearRequestModel;
import com.example.fesc.sigeliFesc.models.respuestas.UsuarioDataRestModel;
import com.example.fesc.sigeliFesc.services.IUsuarioService;
import com.example.fesc.sigeliFesc.shared.UsuarioDto;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IUsuarioService iUsuarioService;

    @GetMapping()
    public List<UsuarioDataRestModel> verUsuarios(){

        List<UsuarioDataRestModel> usuarioDataRestModelList = new ArrayList<UsuarioDataRestModel>();

        for(UsuarioDto usuarioDto : iUsuarioService.verUsuarios()){

            usuarioDataRestModelList.add(modelMapper.map(usuarioDto, UsuarioDataRestModel.class));

        }

        return usuarioDataRestModelList;

    }

    @GetMapping(path = "/{id}")
    public UsuarioDataRestModel verUsuario(@PathVariable("id")String id){

        UsuarioDto usuarioDto = iUsuarioService.verUsuario(Integer.parseInt(id));

        UsuarioDataRestModel usuarioDataRestModel = modelMapper.map(usuarioDto, UsuarioDataRestModel.class);

        return usuarioDataRestModel;

    }

    @GetMapping("/buscar/{documento}")
    public UsuarioDataRestModel buscarUsuario(@PathVariable("documento")String documento){

        UsuarioDto usuarioDto = iUsuarioService.buscarUsuario(documento);

        UsuarioDataRestModel usuarioDataRestModel = modelMapper.map(usuarioDto, UsuarioDataRestModel.class);

        return usuarioDataRestModel;

    }

    @PostMapping
    public UsuarioDataRestModel crearUsuario(@RequestBody UsuarioCrearRequestModel usuarioCrearRequestModel){

        UsuarioDto usuarioCrearDto = modelMapper.map(usuarioCrearRequestModel, UsuarioDto.class);

        UsuarioDto usuarioDto = iUsuarioService.crearUsuario(usuarioCrearDto);

        UsuarioDataRestModel usuarioDataRestModel = modelMapper.map(usuarioDto, UsuarioDataRestModel.class);

        return usuarioDataRestModel;

    }

    @PostMapping("/actualizar/{documento}")
    public UsuarioDataRestModel actualizarUsuario(@RequestBody UsuarioCrearRequestModel usuarioCrearRequestModel, @PathVariable("documento") String documento){

        UsuarioDto usuarioDtoRequest = modelMapper.map(usuarioCrearRequestModel, UsuarioDto.class);

        UsuarioDto usuarioDtoRest = iUsuarioService.actualizarUsuario(usuarioDtoRequest, documento);

        UsuarioDataRestModel usuarioDataRestModel = modelMapper.map(usuarioDtoRest, UsuarioDataRestModel.class);

        return usuarioDataRestModel;

    }

    @PostMapping("/eliminar/{documento}")
    public UsuarioDataRestModel eliminarUsuario(@PathVariable("documento") String documento){

        UsuarioDto usuarioDto = iUsuarioService.eliminarUsuario(documento);

        UsuarioDataRestModel usuarioDataRestModel = modelMapper.map(usuarioDto, UsuarioDataRestModel.class);

        return usuarioDataRestModel;

    }

    
}
