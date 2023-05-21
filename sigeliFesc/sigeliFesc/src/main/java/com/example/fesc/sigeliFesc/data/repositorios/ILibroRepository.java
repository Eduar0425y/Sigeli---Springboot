package com.example.fesc.sigeliFesc.data.repositorios;



import com.example.fesc.sigeliFesc.data.entidades.LibroEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ILibroRepository extends CrudRepository<LibroEntity, Long> {

    public List<LibroEntity> findByIsbnIgnoreCase(String isbn);

    public LibroEntity findByIsbn(String isbn);

    public List<LibroEntity> findByNombreIgnoreCase(String nombre);

    public List<LibroEntity> findByAutorIgnoreCase(String autor);



}
