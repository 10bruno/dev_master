package fatura.cartao.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EntradaJson {

    private String uuid;
    private String conta;
    private String fatura;
    private String tipoPessoa;
    private String permissao;
    private String origem;

}
