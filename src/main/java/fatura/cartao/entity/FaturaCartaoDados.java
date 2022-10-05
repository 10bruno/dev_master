package fatura.cartao.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
public class FaturaCartaoDados {

    @Id
    @Column(name = "row_num")
    String rowNum;
    @Column(name = "num_conta")
    String numConta;
    @Column(name = "bill_id")
    String billId;
    @Column(name = "due_date")
    LocalDate dueDate;
    @Column(name = "bill_total_amount")
    BigDecimal billTotalAmount;
    @Column(name = "company_cnpj")
    String cnpj;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FaturaCartaoDados that = (FaturaCartaoDados) o;
        return Objects.equals(rowNum, that.rowNum);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
