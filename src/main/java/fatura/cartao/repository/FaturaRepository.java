
package fatura.cartao.repository;

import fatura.cartao.entity.FaturaCartaoDados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FaturaRepository extends JpaRepository<FaturaCartaoDados, String> {

    @Query(value = """
            SELECT rownum AS row_num
            ,f.conta AS num_conta
            ,f.fatura AS bill_id
            ,f.dt_vct AS due_date
            ,f.valor_final AS bill_total_amount
            ,f.cnpj AS company_cnpj
             FROM fatura f
             WHERE 1 = 1
             AND f.conta = :numConta
             AND f.dt_vct >= add_months(SYSDATE, -12)
             ORDER BY f.fatura ASC
             """, nativeQuery = true)
    Optional<List<FaturaCartaoDados>> findByNumConta(@Param("numConta") String numConta);

}
