
package fatura.cartao.repository;

import fatura.cartao.entity.FaturaCartaoEncargos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface EncargosRepository extends JpaRepository<FaturaCartaoEncargos, String> {

    @Query(value = """
            SELECT rownum AS row_num,
                   e.conta AS num_conta,
                   e.fatura AS bill_id,
                   e.val_encargo AS amount,
                   e.tp_encargo AS TYPE,
                   'BRL' AS currency
            FROM encargo_fatura e
            WHERE 1 = 1
            AND e.conta = :numConta
            AND e.fatura IN :faturas
            ORDER BY e.fatura
            """, nativeQuery = true)
    Optional<List<FaturaCartaoEncargos>> findByNumContaAndFaturasEncargos(@Param("numConta") String numConta, @Param("faturas") List<String> faturas);

}
