package fatura.cartao.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
public class FaturaCartaoDados {

    @Id
    @Column(name = "row_num")
    String row_num;
    @Column(name = "num_conta")
    String numConta;
    @Column(name = "bill_id")
    String billId;
    @Column(name = "due_date")
    LocalDate due_date;
    @Column(name = "bill_total_amount")
    BigDecimal billTotalAmount;
    @Column(name = "company_cnpj")
    String cnpj;

}
