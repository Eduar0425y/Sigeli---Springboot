package com.example.fesc.sigeliFesc.services;

import com.example.fesc.sigeliFesc.data.entidades.CategoriaEntity;
import com.example.fesc.sigeliFesc.data.entidades.CategoriaLibroEntity;
import com.example.fesc.sigeliFesc.data.entidades.LibroEntity;
import com.example.fesc.sigeliFesc.data.repositorios.ICategoriaLibroRepository;
import com.example.fesc.sigeliFesc.data.repositorios.ICategoriaRepository;
import com.example.fesc.sigeliFesc.data.repositorios.ILibroRepository;
import com.example.fesc.sigeliFesc.shared.CategoriaDto;
import com.example.fesc.sigeliFesc.shared.CategoriaLibroDto;
import com.example.fesc.sigeliFesc.shared.LibroDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaLibroService implements ICategoriaLibroService{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ICategoriaLibroRepository iCategoriaLibroRepository;

    @Autowired
    ILibroRepository iLibroRepository;

    @Autowired
    ICategoriaRepository iCategoriaRepository;


    @Override
    public List<CategoriaLibroDto> verCategoriaLibro(String isbn) {

        List<CategoriaLibroDto> categoriaLibroDtoList = new ArrayList<>();

        LibroEntity libroEntity =  iLibroRepository.findByIsbn(isbn);

        List<CategoriaLibroEntity> categoriaLibroEntityList = iCategoriaLibroRepository.findByLibroEntity(libroEntity);

        for(CategoriaLibroEntity categoriaLibroEntity : categoriaLibroEntityList){

            CategoriaLibroDto categoriaLibroDto = modelMapper.map(categoriaLibroEntity, CategoriaLibroDto.class);

            categoriaLibroDtoList.add(categoriaLibroDto);

        }

        return categoriaLibroDtoList;

    }

    @Override
    public List<CategoriaLibroDto> verLibrosCategoria(int idCategoria) {

        List<CategoriaLibroDto> categoriaLibroDtoList = new ArrayList<>();

        CategoriaEntity categoriaEntity = iCategoriaRepository.findById(idCategoria);

        List<CategoriaLibroEntity> categoriaLibroEntityList = iCategoriaLibroRepository.findByCategoriaEntity(categoriaEntity);

        for(CategoriaLibroEntity categoriaLibroEntity : categoriaLibroEntityList){

            CategoriaLibroDto categoriaLibroDto = modelMapper.map(categoriaLibroEntity, CategoriaLibroDto.class);

            categoriaLibroDtoList.add(categoriaLibroDto);

        }

        return categoriaLibroDtoList;

    }

    @Override
    public CategoriaLibroDto crearCategoriaLibro(CategoriaLibroDto categoriaLibroDto) {


        LibroEntity libroEntity = iLibroRepository.findByIsbn(categoriaLibroDto.getLibroIsbn());
        LibroDto libroDto = modelMapper.map(libroEntity, LibroDto.class);
        Optional<CategoriaEntity> categoriaEntity = iCategoriaRepository.findById(categoriaLibroDto.getCategoriaId());
        CategoriaDto categoriaDto = modelMapper.map(categoriaEntity, CategoriaDto.class);

        categoriaLibroDto.setCategoriaEntity(categoriaDto);
        categoriaLibroDto.setLibroEntity(libroDto);

        CategoriaLibroEntity categoriaLibroEntity = modelMapper.map(categoriaLibroDto, CategoriaLibroEntity.class);

        CategoriaLibroEntity categoriaLibroEntityRest = iCategoriaLibroRepository.save(categoriaLibroEntity);

        CategoriaLibroDto categoriaLibroDtoRest = modelMapper.map(categoriaLibroEntityRest, CategoriaLibroDto.class);

        return categoriaLibroDtoRest;

    }

    @Override
    public String eliminarCategoriaLibro(CategoriaLibroDto categoriaLibroDto) {

        CategoriaLibroEntity categoriaLibroEntityDto = modelMapper.map(categoriaLibroDto, CategoriaLibroEntity.class);


        iCategoriaLibroRepository.delete(categoriaLibroEntityDto);


        return "categoría del Libro eliminada con éxito";


    }
}
