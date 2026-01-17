// 1. Onde a classe mora
package br.com.serverest.api.data;

import br.com.serverest.api.model.UsuarioModel;
import com.github.javafaker.Faker;

public class UsuarioDataFactory {

    private static final Faker faker = new Faker();

    public static UsuarioModel criarUsuarioDadosDinamicos() {
        return new UsuarioModel(
                faker.name()
                        .fullName(),
                faker.internet()
                        .emailAddress(),
                "teste123",
                "true"
        );
    }
}