package com.example.fesc.sigeliFesc.services;

import java.util.List;

import com.example.fesc.sigeliFesc.shared.UsuarioDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUsuarioService extends UserDetailsService {

    public UsuarioDto crearUsuario(UsuarioDto usuarioCrearDto);
    public List<UsuarioDto> verUsuarios();
    public UsuarioDto verUsuario(int id);
    public UsuarioDto buscarUsuario(String documento);
    public UsuarioDto actualizarUsuario(UsuarioDto usuarioActualizarDto, String documento);
    public UsuarioDto eliminarUsuario(String documento);

}
