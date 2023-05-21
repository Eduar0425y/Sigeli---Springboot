package com.example.fesc.sigeliFesc.services;

import com.example.fesc.sigeliFesc.data.entidades.LibroEntity;
import com.example.fesc.sigeliFesc.data.repositorios.ILibroRepository;
import com.example.fesc.sigeliFesc.shared.LibroDto;
import com.example.fesc.sigeliFesc.shared.UsuarioDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class LibroService implements ILibroService{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ILibroRepository iLibroRepository;

    @Override
    public List<LibroDto> verLibros() {

        List<LibroDto> libroDtoList = new ArrayList<>();

        for(LibroEntity libroEntity : iLibroRepository.findAll()){

            LibroDto libroDto = modelMapper.map(libroEntity, LibroDto.class);

            libroDtoList.add(libroDto);

        }

        return libroDtoList;

    }

    @Override
    public LibroDto crearLibro(LibroDto libroDto) {

        LibroEntity libroEntityDto = modelMapper.map(libroDto, LibroEntity.class);

        LibroEntity libroEntity = iLibroRepository.save(libroEntityDto);

        LibroDto libroDtoRest = modelMapper.map(libroEntity, LibroDto.class);

        return libroDtoRest;

    }

    @Override
    public List<LibroDto> bucarLibroModo(String palabra, int modo) {

        if(modo == 1){
            List<LibroDto> libroDtoList = new ArrayList<>();

            for(LibroEntity libroEntity : iLibroRepository.findByIsbnIgnoreCase(palabra)){

                LibroDto libroDto = modelMapper.map(libroEntity, LibroDto.class);

                libroDtoList.add(libroDto);

            }

            if(libroDtoList == null){
                throw new RuntimeException("No se han encontrado libros con " + palabra);
            }

            else{
                return libroDtoList;
            }

        }

        if(modo == 2){

            List<LibroDto> libroDtoList = new ArrayList<>();

            for(LibroEntity libroEntity : iLibroRepository.findByAutorIgnoreCase(palabra)){

                LibroDto libroDto = modelMapper.map(libroEntity, LibroDto.class);

                libroDtoList.add(libroDto);

            }

            if(libroDtoList == null){
                throw new RuntimeException("No se han encontrado libros con " + palabra);
            }

            else{
                return libroDtoList;
            }

        }

        if (modo == 3){

            List<LibroDto> libroDtoList = new ArrayList<>();

            for(LibroEntity libroEntity : iLibroRepository.findByNombreIgnoreCase(palabra)){

                LibroDto libroDto = modelMapper.map(libroEntity, LibroDto.class);

                libroDtoList.add(libroDto);

            }

            if(libroDtoList == null){
                throw new RuntimeException("No se han encontrado libros con " + palabra);
            }

            else{
                return libroDtoList;
            }


        }

        else{
            throw new RuntimeException("El modo no existe");
        }

    }

    @Override
    public List<LibroDto> bucarLibro(String palabra) {

        List<LibroDto> libroDtoList = new ArrayList<>();
        List <LibroEntity> libroEntityList = new ArrayList<>();

        String busqueda = ".*" + palabra + ".*";

        for(LibroEntity libroEntity : iLibroRepository.findByNombreIgnoreCase(busqueda)){

            LibroDto libroDto = modelMapper.map(libroEntity, LibroDto.class);

            libroDtoList.add(libroDto);

        }

        for(LibroEntity libroEntity : iLibroRepository.findByIsbnIgnoreCase(busqueda)){

            LibroDto libroDto = modelMapper.map(libroEntity, LibroDto.class);

            libroDtoList.add(libroDto);

        }

        for(LibroEntity libroEntity : iLibroRepository.findByAutorIgnoreCase(busqueda)){

            LibroDto libroDto = modelMapper.map(libroEntity, LibroDto.class);

            libroDtoList.add(libroDto);

        }

        if(libroDtoList == null){
            throw new RuntimeException("No se ha encontrado ningún libro");
        }

        else {
            return libroDtoList;
        }

    }

    @Override
    public LibroDto actualizarLibro(LibroDto libroDto) {

        LibroDto libroDtoRest = new LibroDto();

        LibroEntity libroEntity= (LibroEntity) iLibroRepository.findByIsbnIgnoreCase(libroDto.getIsbn());

        if(libroEntity == null){

            throw new RuntimeException("Este libro no está en la bd");

        }

        else if(libroEntity.getEstado() == 1){

            libroEntity.setNombre(libroDto.getNombre());
            libroEntity.setEstante(libroDto.getEstante());
            libroEntity.setFila(libroDto.getFila());

            iLibroRepository.save(libroEntity);

            libroDtoRest = modelMapper.map(libroEntity, LibroDto.class);

            return libroDtoRest;

        }
        else if (libroEntity.getEstado() == 0){

            throw new RuntimeException("Este libro Fue eliminado de la bd");

        }


        return libroDtoRest;
    }

    @Override
    public String cambiarEstado(String isbn, int estado) {
        LibroDto libroDtoRest = new LibroDto();

        LibroEntity libroEntity= (LibroEntity) iLibroRepository.findByIsbnIgnoreCase(isbn);

        if(libroEntity == null){

            throw new RuntimeException("Este libro no está en la bd");

        }

        else if(libroEntity.getEstado() == 1){

            libroEntity.setEstado(estado);

            iLibroRepository.save(libroEntity);


            return "Libro actualzado con éxito";

        }
        else if (libroEntity.getEstado() == 0){

            throw new RuntimeException("Este libro Fue eliminado de la bd");

        }


        return "Error";
    }
}
