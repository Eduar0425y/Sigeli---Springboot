package com.example.fesc.sigeliFesc.seguridad;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class TokenAutorizacion extends BasicAuthenticationFilter {

    public TokenAutorizacion(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException{

        String header = request.getHeader(ConstantesSecurity.HEADER_STRING);

        if(header == null || !header.startsWith(ConstantesSecurity.TOKEN_PREFIJO)){

            chain.doFilter(request, response);

            return;

        }

        UsernamePasswordAuthenticationToken authenticationToken = getAutenticatinToken(header);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAutenticatinToken(String header){

        if(header!=null){

            String token = header.replace(ConstantesSecurity.TOKEN_PREFIJO, "");

            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(ConstantesSecurity.TOKEN_SECRETO));

            String usuario = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();

            if(usuario!=null){
                return new UsernamePasswordAuthenticationToken(usuario, null, new ArrayList<>());
            }

        }

        return null;
    }
}
