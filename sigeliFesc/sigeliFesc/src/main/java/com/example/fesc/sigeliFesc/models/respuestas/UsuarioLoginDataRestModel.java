package com.example.fesc.sigeliFesc.models.respuestas;

public class UsuarioLoginDataRestModel {
    
    private String documento;
    private String correo;
    private String password;


    public String getDocumento() {
        return this.documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
