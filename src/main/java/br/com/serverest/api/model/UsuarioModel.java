package br.com.serverest.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioModel {
    private String nome;
    private String email;
    private String password;
    private String administrador;
}