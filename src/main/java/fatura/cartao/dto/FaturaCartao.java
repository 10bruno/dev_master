package fatura.cartao.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class FaturaCartao {

    private final String type = "CREDIT_CARD_ACCOUNT_BILL";
    private Data data;
    private Meta meta;

}