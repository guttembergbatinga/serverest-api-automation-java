package br.com.serverest.api.dataFactory;

import br.com.serverest.api.model.CarrinhosModel;
import br.com.serverest.api.model.ProdutoCarrinhoModel;

import java.util.ArrayList;
import java.util.List;

public class CarrinhosDataFactory {

    public static CarrinhosModel gerarCarrinhoComUmProduto(String idProduto, int quantidade) {

        ProdutoCarrinhoModel item = new ProdutoCarrinhoModel(idProduto, quantidade);

        List<ProdutoCarrinhoModel> listaProdutos = new ArrayList<>();
        listaProdutos.add(item);

        CarrinhosModel carrinho = new CarrinhosModel();
        carrinho.setProdutos(listaProdutos);

        return carrinho;
    }
}