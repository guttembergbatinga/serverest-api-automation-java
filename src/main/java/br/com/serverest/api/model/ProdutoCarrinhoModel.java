package br.com.serverest.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoCarrinhoModel {
    private String idProduto;
    private int quantidade;
}