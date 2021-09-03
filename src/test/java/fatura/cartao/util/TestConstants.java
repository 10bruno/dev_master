package fatura.cartao.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestConstants {

    public static final String json = "{\n" +
            "  \"uuid\":\"sadb8796-asmdnbsam-sadb8796-asmdnbsam\",\n" +
            "  \"conta\": \"987987987987987987\",\n" +
            "  \"tipoPessoa\": \"F\",\n" +
            "  \"permissao\":\"CREDIT_CARDS_ACCOUNTS_BILLS_READ\",\n" +
            "  \"origem\":\"cadastro\"\n" +
            "}";

    public static final String UUID = "sadb8796-asmdnbsam-sadb8796-asmdnbsam";
    public static final String numConta = "987987987987987987";
    public static final String fatura = "17";
    public static final String tipoPessoa = "F";
    public static final String permissao = "CREDIT_CARDS_ACCOUNTS_BILLS_READ";
    public static final String origem = "cadastro";

    //Entity FaturaCartaoDados
    public static final String row_num = "1";
    public static final String numContaRepo = "0004960450097080003";
    public static final String billIdRepo = "17";
    public static final LocalDate due_date = LocalDate.of(2021, 12, 10);
    public static final BigDecimal billTotalAmount = new BigDecimal(1204.80000);
    public static final String companyCnpj = "23646564000109";
    public static final String encargoType = "OUTROS_TARIFA";
    public static final String currency = "BRL";

    //DTO FinanceCharges
    public static final String type = "OUTROS_TARIFA";
    public static final String additionalInfo = "Tarifa bancaria";
    public static final BigDecimal chargeAmount = new BigDecimal(276.0000);
    public static final String chargeCurrency = "BRL";

    public static final LocalDateTime localDateTime = LocalDateTime.now();

    //consumer
    public static final String TOPICO_IN = "topic";
    public static final int PARTICAO = 1;
    public static final long OFFSET = 0;
    public static final String KEY = UUID;

    public static final String typeIOF = "IOF";
    public static final String semEncargo = "SEM_ENCARGO";

}
