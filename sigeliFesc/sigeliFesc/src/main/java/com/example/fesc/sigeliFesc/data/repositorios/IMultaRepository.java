package com.example.fesc.sigeliFesc.data.repositorios;

import com.example.fesc.sigeliFesc.data.entidades.MultaEntity;
import com.example.fesc.sigeliFesc.data.entidades.PrestamoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IMultaRepository extends CrudRepository<MultaEntity, Long> {

    public MultaEntity findById(long id);
    public MultaEntity findByPrestamoEntity(PrestamoEntity prestamoEntity);

    public List<MultaEntity> findByIdEstado(long id);

}
