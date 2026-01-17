package br.com.serverest.api.model;

import com.fasterxml.jackson.annotation.JsonInclude; // Import necessário
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) //
public class CarrinhosModel {
    private List<ProdutoCarrinhoModel> produtos;
    private String id;
    private Integer precoTotal;
    private Integer quantidadeTotal;
    private String idUsuario;
}