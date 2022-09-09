package fatura.cartao.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class FaturaCartao {

    private static final String TYPE = "CREDIT_CARD_ACCOUNT_BILL";
    private Data data;
    private Meta meta;

}