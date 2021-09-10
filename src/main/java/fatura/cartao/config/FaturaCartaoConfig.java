package fatura.cartao.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Validated
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ConfigurationProperties(prefix = "fatura-cartao.topic")
public class FaturaCartaoConfig {

    @NotEmpty
    private String consumer;

    @NotEmpty
    private String producer;

    @NotEmpty
    private String error;

}
