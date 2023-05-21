package com.example.fesc.sigeliFesc.data.repositorios;


import com.example.fesc.sigeliFesc.data.entidades.CategoriaEntity;
import com.example.fesc.sigeliFesc.data.entidades.CategoriaLibroEntity;
import com.example.fesc.sigeliFesc.data.entidades.LibroEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ICategoriaLibroRepository extends CrudRepository<CategoriaLibroEntity, Long> {

    public List<CategoriaLibroEntity> findByLibroEntity(LibroEntity libroEntity);

    public List<CategoriaLibroEntity> findByCategoriaEntity(CategoriaEntity categoriaEntity);

}
