package com.example.fesc.sigeliFesc.data.repositorios;

import com.example.fesc.sigeliFesc.data.entidades.CategoriaEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICategoriaRepository  extends CrudRepository<CategoriaEntity, Long> {

    public CategoriaEntity findByNombre(String nombre);

    public CategoriaEntity findById(int id);

}
