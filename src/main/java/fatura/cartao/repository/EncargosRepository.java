
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

    @Query(value = "SELECT rownum AS row_num\n" +
            ",e.conta   AS num_conta\n" +
            ",e.fatura  AS bill_id\n" +
            ",e.val_encargo AS amount\n" +
            ",e.tp_encargo  AS TYPE\n" +
            ",'BRL' AS currency\n" +
            "  FROM encargo_fatura e\n" +
            " WHERE 1 = 1\n" +
            "   AND e.conta = :numConta\n" +
            "   AND e.fatura IN :faturas\n" +
            " ORDER BY e.fatura\n", nativeQuery = true)
    Optional<List<FaturaCartaoEncargos>> findByNumContaAndFaturasEncargos(@Param("numConta") String numConta, @Param("faturas") List<String> faturas);

}
