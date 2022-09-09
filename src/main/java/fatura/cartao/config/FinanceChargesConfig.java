package fatura.cartao.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ConfigurationProperties(prefix = "fatura-cartao.finance-charges")
public class FinanceChargesConfig {
    private List<Tipos> tipos;

    @Data
    public static class Tipos {
        private String nome;
        private String descricao;
    }
}