package fatura.cartao.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FinanceCharges {

    private String type;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String additionalInfo;
    private BigDecimal amount;
    private String currency;

}
