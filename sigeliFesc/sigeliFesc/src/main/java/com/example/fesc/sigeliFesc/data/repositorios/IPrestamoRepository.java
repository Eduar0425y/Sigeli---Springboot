package com.example.fesc.sigeliFesc.data.repositorios;

import com.example.fesc.sigeliFesc.data.entidades.LibroEntity;
import com.example.fesc.sigeliFesc.data.entidades.PrestamoEntity;
import com.example.fesc.sigeliFesc.data.entidades.UsuarioEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface IPrestamoRepository extends CrudRepository<PrestamoEntity, Long> {

    public PrestamoEntity findById(long id);

    public List<PrestamoEntity> findByUsuarioEntity(UsuarioEntity usuarioEntity);

    public List<PrestamoEntity> findByLibroEntity(LibroEntity libroEntity);

    public List<PrestamoEntity> findByIdEstado(int estado);


}
