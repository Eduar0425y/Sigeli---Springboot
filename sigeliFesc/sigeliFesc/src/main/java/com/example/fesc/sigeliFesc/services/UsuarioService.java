package com.example.fesc.sigeliFesc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.fesc.sigeliFesc.data.entidades.UsuarioEntity;
import com.example.fesc.sigeliFesc.data.repositorios.IUsuarioRepository;
import com.example.fesc.sigeliFesc.shared.UsuarioDto;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IUsuarioRepository iUsuarioRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UsuarioDto crearUsuario(UsuarioDto usuarioCrearDto) {

        if(iUsuarioRepository.findByDocumento(usuarioCrearDto.getDocumento()) != null){
            throw new RuntimeException("Este documento ya esta en uso");
        }

        if(iUsuarioRepository.findByCorreo(usuarioCrearDto.getCorreo()) != null){
            throw new RuntimeException("Este correo ya esta en uso");
        }
        
        UsuarioEntity usuarioEntityDto = modelMapper.map(usuarioCrearDto, UsuarioEntity.class);
        usuarioEntityDto.setIdUsuario(UUID.randomUUID().toString());
        usuarioEntityDto.setpasswordEncriptada(bCryptPasswordEncoder.encode(usuarioCrearDto.getPassword()));
        usuarioEntityDto.setEstadoCuenta(1);

        UsuarioEntity usuarioEntity = iUsuarioRepository.save(usuarioEntityDto);

        UsuarioDto usuarioDto = modelMapper.map(usuarioEntity, UsuarioDto.class);

        return usuarioDto;
        
    }

    @Override
    public List<UsuarioDto> verUsuarios() {

        List<UsuarioDto> usuarioDtoList = new ArrayList<UsuarioDto>();

        for (UsuarioEntity usuarioEntity : iUsuarioRepository.findAll()){

            if(usuarioEntity.getEstadoCuenta() == 1){

                usuarioDtoList.add(modelMapper.map(usuarioEntity, UsuarioDto.class));

            }

        }
        
        return usuarioDtoList;
    }

    @Override
    public UsuarioDto verUsuario(int id) {

        UsuarioEntity usuarioEntity = iUsuarioRepository.findById(id);

        if (usuarioEntity == null){

            throw new RuntimeException("Esta persona no está en la bd");

        }

        else if(usuarioEntity.getEstadoCuenta() == 1){

            UsuarioDto usuarioDto = modelMapper.map(usuarioEntity, UsuarioDto.class);

            return usuarioDto;

        }
        else if (usuarioEntity.getEstadoCuenta() == 0){

            throw new RuntimeException("Esta persona Fue eliminada de la bd");

        }

        else {

            throw new RuntimeException("Esta persona no está en la bd");

        }


    }

    @Override
    public UsuarioDto buscarUsuario(String documento) {

        UsuarioEntity usuarioEntity = iUsuarioRepository.findByDocumento(documento);

        if (usuarioEntity == null){

            throw new RuntimeException("Esta persona no está en la bd");

        }

        else if(usuarioEntity.getEstadoCuenta() == 1){

            UsuarioDto usuarioDto = modelMapper.map(usuarioEntity, UsuarioDto.class);

            return usuarioDto;

        }
        else if (usuarioEntity.getEstadoCuenta() == 0){

            throw new RuntimeException("Esta persona Fue eliminada de la bd");

        }

        else {

            throw new RuntimeException("Esta persona no está en la bd");

        }

    }

    @Override
    public UsuarioDto actualizarUsuario(UsuarioDto usuarioActualizarDto, String documento) {
        
        UsuarioEntity usuarioEntity = iUsuarioRepository.findByDocumento(documento);

        if (usuarioEntity == null){

            throw new RuntimeException("Esta persona no está en la bd");

        }

        else if(usuarioEntity.getEstadoCuenta() == 1){

            usuarioEntity.setNombre(usuarioActualizarDto.getNombre());
            usuarioEntity.setApellido(usuarioActualizarDto.getApellido());
            usuarioEntity.setCorreo(usuarioActualizarDto.getCorreo());
            usuarioEntity.setTelefono(usuarioActualizarDto.getTelefono());
            usuarioEntity.setpasswordEncriptada(bCryptPasswordEncoder.encode(usuarioActualizarDto.getPassword()));

            iUsuarioRepository.save(usuarioEntity);

            UsuarioDto usuarioDto = modelMapper.map(usuarioEntity, UsuarioDto.class);

            return usuarioDto;

        }
        else if (usuarioEntity.getEstadoCuenta() == 0){

            throw new RuntimeException("Esta persona Fue eliminada de la bd");

        }


        else {

            throw new RuntimeException("Esta persona no está en la bd");

        }



    }

    @Override
    public UsuarioDto eliminarUsuario(String documento) {

        UsuarioEntity usuarioEntity = iUsuarioRepository.findByDocumento(documento);

        if (usuarioEntity == null){

            throw new RuntimeException("Esta persona no está en la bd");

        }

        else if(usuarioEntity.getEstadoCuenta() == 1){

            usuarioEntity.setEstadoCuenta(0);

            iUsuarioRepository.save(usuarioEntity);

            UsuarioDto usuarioDto = modelMapper.map(usuarioEntity, UsuarioDto.class);

            return usuarioDto;

        }
        else if (usuarioEntity.getEstadoCuenta() == 0){

            throw new RuntimeException("Esta persona Fue eliminada de la bd");

        }

        else {

            throw new RuntimeException("Esta persona no está en la bd");

        }

    }

    
    
}
