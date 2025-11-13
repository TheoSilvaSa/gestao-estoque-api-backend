package com.senai.gestao_estoque_api.dto;

import com.senai.gestao_estoque_api.model.Perfil;
import com.senai.gestao_estoque_api.model.Usuario;

public class UsuarioResponseDto {

    private Long id;
    private String nomeCompleto;
    private String email;
    private Perfil perfil;
    private Boolean ativo;

    public UsuarioResponseDto() {
    }

    public UsuarioResponseDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nomeCompleto = usuario.getNomeCompleto();
        this.email = usuario.getEmail();
        this.perfil = usuario.getPerfil();
        this.ativo = usuario.getAtivo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
