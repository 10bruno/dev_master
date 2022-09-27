package fatura.cartao.util;

import fatura.cartao.dto.*;
import fatura.cartao.entity.FaturaCartaoDados;
import fatura.cartao.entity.FaturaCartaoEncargos;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MockBuilders {

    public static String buildMockJson() {
        return TestConstants.JSON;
    }

    public static EntradaJson buildMockEntradaJson() {
        return EntradaJson.builder()
                .uuid(TestConstants.UUID)
                .conta(TestConstants.NUM_CONTA)
                .fatura(TestConstants.FATURA)
                .tipoPessoa(TestConstants.TIPO_PESSOA)
                .permissao(TestConstants.PERMISSAO)
                .origem(TestConstants.ORIGEM)
                .build();
    }

    public static FaturaCartaoDados buildMockFaturaCartaoDados() {
        return FaturaCartaoDados.builder()
                .rowNum(TestConstants.ROW_NUM)
                .numConta(TestConstants.NUM_CONTA_REPO)
                .billId(TestConstants.BILL_ID_REPO)
                .dueDate(TestConstants.DUE_DATE)
                .billTotalAmount(TestConstants.BILL_TOTAL_AMOUNT)
                .cnpj(TestConstants.COMPANY_CNPJ)
                .build();
    }

    public static List<FaturaCartaoDados> buildMockListFaturaCartaoDados() {
        return Stream.of(buildMockFaturaCartaoDados()).collect(Collectors.toList());
    }

    public static Data buildMockData() {
        return Data.builder()
                .billId(TestConstants.FATURA)
                .dueDate(TestConstants.DUE_DATE)
                .billTotalAmount(TestConstants.BILL_TOTAL_AMOUNT)
                .financeCharges(buildMockListFinanceCharges())
                .build();
    }

    public static FinanceCharges buildMockFinanceCharges() {
        return FinanceCharges.builder()
                .type(TestConstants.TYPE)
                .additionalInfo(TestConstants.ADDITIONAL_INFO)
                .amount(TestConstants.CHARGE_AMOUNT)
                .currency(TestConstants.CHARGE_CURRENCY)
                .build();
    }

    public static List<FinanceCharges> buildMockListFinanceCharges() {
        return Stream.of(buildMockFinanceCharges()).collect(Collectors.toList());

    }

    public static Meta buildMockMeta() {
        return Meta.builder()
                .uuid(TestConstants.UUID)
                .sourceTimestamp(TestConstants.LOCAL_DATE_TIME)
                .cnpj(TestConstants.COMPANY_CNPJ)
                .build();
    }

    public static FaturaCartao buildMockFaturaCartao() {
        return new FaturaCartao(buildMockData(), buildMockMeta());
    }

    public static List<FaturaCartao> buildMockListFaturaCartao() {
        return Stream.of(buildMockFaturaCartao()).collect(Collectors.toList());
    }

    public static ArrayList<String> buildMockFaturas() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(TestConstants.FATURA);
        return arrayList;
    }

    public static FaturaCartaoEncargos buildMockFaturaCartaoEncargos() {
        return FaturaCartaoEncargos.builder()
                .rowNum(TestConstants.ROW_NUM)
                .numConta(TestConstants.NUM_CONTA)
                .billId(TestConstants.FATURA)
                .type(TestConstants.ENCARGO_TYPE)
                .currency(TestConstants.CURRENCY)
                .build();
    }

    public static List<FaturaCartaoEncargos> buildMockListFaturaCartaoEncargos() {
        return Stream.of(buildMockFaturaCartaoEncargos()).collect(Collectors.toList());
    }

    public static ConsumerRecord<String, String> buildConsumerRecord() {
        return new ConsumerRecord<>(
                TestConstants.TOPICO_IN,
                TestConstants.PARTICAO,
                TestConstants.OFFSET,
                TestConstants.KEY,
                buildMockJson());
    }
}
