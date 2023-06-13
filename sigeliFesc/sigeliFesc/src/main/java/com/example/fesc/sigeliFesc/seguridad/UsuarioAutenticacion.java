package com.example.fesc.sigeliFesc.seguridad;


import com.example.fesc.sigeliFesc.models.peticiones.UsuarioSingupRequestModel;
import com.example.fesc.sigeliFesc.services.IUsuarioService;
import com.example.fesc.sigeliFesc.services.UsuarioService;
import com.example.fesc.sigeliFesc.shared.UsuarioDto;
import com.example.fesc.sigeliFesc.utils.AppContexto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.PrintWriter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

public class UsuarioAutenticacion extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;


    public UsuarioAutenticacion(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try{
            UsuarioSingupRequestModel usuarioSingupRequestModel = new ObjectMapper().readValue(
                    request.getInputStream(), UsuarioSingupRequestModel.class
            );
            UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                    usuarioSingupRequestModel.getUsername(),
                    usuarioSingupRequestModel.getPassword(),
                    new ArrayList<>()
            );

            Authentication authentication = authenticationManager.authenticate(upat);

            return authentication;

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException{

        String username = ((User) authResult.getPrincipal()).getUsername();

        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(ConstantesSecurity.TOKEN_SECRETO));

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+ConstantesSecurity.FECHA_EXPIRACION))
                .signWith(secretKey)
                .compact();



        response.addHeader("Acces-Control-Expose-Headers", "Authorization");
        response.addHeader(ConstantesSecurity.HEADER_STRING, ConstantesSecurity.TOKEN_PREFIJO + token);
        response.addHeader("Content-Type", "application/json");
        PrintWriter  witer = response.getWriter();
        witer.println("{\"token\" : \"" + token + "\"}");
        witer.close();
    }



}
