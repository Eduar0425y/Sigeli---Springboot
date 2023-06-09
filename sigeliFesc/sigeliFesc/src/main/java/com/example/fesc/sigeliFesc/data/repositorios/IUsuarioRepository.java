package com.example.fesc.sigeliFesc.data.repositorios;

import org.springframework.data.repository.CrudRepository;

import com.example.fesc.sigeliFesc.data.entidades.UsuarioEntity;

public interface IUsuarioRepository extends CrudRepository<UsuarioEntity, Long>{

    public UsuarioEntity findByDocumento(String documento);
    public UsuarioEntity findByCorreo(String correo);
    public UsuarioEntity findByNombre(String nombre);
    public UsuarioEntity findById(int id);
    
}
