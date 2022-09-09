
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
    @Query(value = "SELECT rownum AS row_num\n" +
            ",f.conta AS num_conta\n" +
            ",f.fatura AS bill_id\n" +
            ",f.dt_vct AS due_date\n" +
            ",f.valor_final AS bill_total_amount\n" +
            ",f.cnpj AS company_cnpj\n" +
            " FROM fatura f\n" +
            " WHERE 1 = 1\n" +
            " AND f.conta = :numConta\n" +
            " AND f.dt_vct >= add_months(SYSDATE, -12)\n" +
            " ORDER BY f.fatura ASC\n", nativeQuery = true)
    Optional<List<FaturaCartaoDados>> findByNumConta(@Param("numConta") String numConta);

}
