package fatura.cartao.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fatura.cartao.dto.serializer.CustomLocalDateSerializer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Meta {

    private String uuid;
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDateTime sourceTimestamp;
    private String cnpj;

}
