package br.com.serverest.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoModel {
    private String nome;
    private int preco;
    private String descricao;
    private int quantidade;
}