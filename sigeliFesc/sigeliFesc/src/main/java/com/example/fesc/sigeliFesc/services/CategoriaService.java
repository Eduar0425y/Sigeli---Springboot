package com.example.fesc.sigeliFesc.services;

import com.example.fesc.sigeliFesc.data.entidades.CategoriaEntity;
import com.example.fesc.sigeliFesc.data.repositorios.ICategoriaRepository;
import com.example.fesc.sigeliFesc.shared.CategoriaDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService implements ICategoriaService{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ICategoriaRepository iCategoriaRepository;

    @Override
    public CategoriaDto crearCategoria(CategoriaDto categoriaCrearDto) {

        if(iCategoriaRepository.findByNombre(categoriaCrearDto.getNombre()) != null){
            throw new RuntimeException("Esta categoría ya ha sido creada");
        }

        CategoriaEntity categoriaEntityDto = modelMapper.map(categoriaCrearDto, CategoriaEntity.class);

        CategoriaEntity categoriaEntity = iCategoriaRepository.save(categoriaEntityDto);

        CategoriaDto categoriaDto = modelMapper.map(categoriaEntity, CategoriaDto.class);

        return categoriaDto;

    }

    @Override
    public List<CategoriaDto> verCategorias() {

        List<CategoriaDto> categoriaDtoList = new ArrayList<>();


        for(CategoriaEntity categoriaEntity: iCategoriaRepository.findAll()){

            CategoriaDto  categoriaDto = modelMapper.map(categoriaEntity, CategoriaDto.class);

            categoriaDtoList.add(categoriaDto);

        }

        return categoriaDtoList;

    }

    @Override
    public CategoriaDto buscarCategoria(String nombre) {

        CategoriaEntity categoriaEntity = iCategoriaRepository.findByNombre(nombre);

        CategoriaDto categoriaDto = modelMapper.map(categoriaEntity, CategoriaDto.class);

        return categoriaDto;

    }

    @Override
    public CategoriaDto editarCategoria(CategoriaDto categoriaDto) {

        if(iCategoriaRepository.findByNombre(categoriaDto.getNombre()) != null){
            throw new RuntimeException("Esta categoría ya ha sido creada");
        }

        CategoriaEntity categoriaEntity = (CategoriaEntity) iCategoriaRepository.findById(categoriaDto.getId());

        categoriaEntity.setNombre(categoriaDto.getNombre());

        iCategoriaRepository.save(categoriaEntity);

        CategoriaDto categoriaDtoReturn = modelMapper.map(categoriaEntity, CategoriaDto.class);

        return categoriaDtoReturn;

    }
}
