package fatura.cartao.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class TestConstants {

    public static final String JSON = """
            {
             "uuid":"sadb8796-asmdnbsam-sadb8796-asmdnbsam",
             "conta": "987987987987987987",
             "tipoPessoa": "F",
             "permissao":"CREDIT_CARDS_ACCOUNTS_BILLS_READ",
             "origem":"cadastro"
            }
            """;
    public static final String NUM_CONTA = "987987987987987987";
    public static final String FATURA = "17";
    public static final String TIPO_PESSOA = "F";
    public static final String PERMISSAO = "CREDIT_CARDS_ACCOUNTS_BILLS_READ";
    public static final String ORIGEM = "cadastro";
    public static final String ROW_NUM = "1";
    public static final String NUM_CONTA_REPO = "0004960450097080003";
    public static final String BILL_ID_REPO = "17";
    public static final LocalDate DUE_DATE = LocalDate.of(2021, 12, 10);
    public static final BigDecimal BILL_TOTAL_AMOUNT = new BigDecimal("1204.80000");
    public static final String COMPANY_CNPJ = "23646564000109";
    public static final String ENCARGO_TYPE = "OUTROS_TARIFA";
    public static final String CURRENCY = "BRL";
    public static final String TYPE = "OUTROS_TARIFA";
    public static final String ADDITIONAL_INFO = "Tarifa bancaria";
    public static final BigDecimal CHARGE_AMOUNT = new BigDecimal("276.0000");
    public static final String CHARGE_CURRENCY = "BRL";
    public static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();
    public static final String TOPICO_IN = "topic";
    public static final int PARTICAO = 1;
    public static final long OFFSET = 0;
    public static final String KEY = UUID.randomUUID().toString();
    public static final String TYPE_IOF = "IOF";
    public static final String SEM_ENCARGO = "SEM_ENCARGO";

}
