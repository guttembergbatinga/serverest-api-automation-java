package br.com.serverest.api.data;

import br.com.serverest.api.model.ProdutoModel;
import com.github.javafaker.Faker;

import java.util.Locale;

public class ProdutoDataFactory {

    private static final Faker faker = new Faker(new Locale("pt-BR"));

    public static ProdutoModel criarProdutoDinamico() {
        return new ProdutoModel(
                faker.commerce()
                        .productName(),
                faker.number()
                        .numberBetween(10, 5000),
                faker.commerce()
                        .material(),
                faker.number()
                        .numberBetween(1, 100)
        );
    }
}