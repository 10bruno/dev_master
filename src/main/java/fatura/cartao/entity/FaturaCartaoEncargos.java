package fatura.cartao.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@Builder
public class FaturaCartaoEncargos {

    @Id
    @Column(name = "row_num")
    String rowNum;
    @Column(name = "num_conta")
    String numConta;
    @Column(name = "bill_id")
    String billId;
    @Column(name = "amount")
    BigDecimal amount;
    @Column(name = "type")
    String type;
    @Column(name = "currency")
    String currency;

}
