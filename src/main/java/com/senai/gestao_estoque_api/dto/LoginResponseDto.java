package com.senai.gestao_estoque_api.dto;

public class LoginResponseDto {

    private String token;
    private String nomeUsuario;
    private String perfilUsuario;

    public LoginResponseDto() {
    }

    public LoginResponseDto(String token, String nomeUsuario, String perfilUsuario) {
        this.token = token;
        this.nomeUsuario = nomeUsuario;
        this.perfilUsuario = perfilUsuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getPerfilUsuario() {
        return perfilUsuario;
    }

    public void setPerfilUsuario(String perfilUsuario) {
        this.perfilUsuario = perfilUsuario;
    }
}