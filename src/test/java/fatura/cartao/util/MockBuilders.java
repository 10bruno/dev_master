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
        return TestConstants.json;
    }

    public static EntradaJson buildMockEntradaJson() {
        return EntradaJson.builder()
                .uuid(TestConstants.UUID)
                .conta(TestConstants.numConta)
                .fatura(TestConstants.fatura)
                .tipoPessoa(TestConstants.tipoPessoa)
                .permissao(TestConstants.permissao)
                .origem(TestConstants.origem)
                .build();
    }

    public static FaturaCartaoDados buildMockFaturaCartaoDados() {
        return FaturaCartaoDados.builder()
                .row_num(TestConstants.row_num)
                .numConta(TestConstants.numContaRepo)
                .billId(TestConstants.billIdRepo)
                .due_date(TestConstants.due_date)
                .billTotalAmount(TestConstants.billTotalAmount)
                .cnpj(TestConstants.companyCnpj)
                .build();
    }

    public static List<FaturaCartaoDados> buildMockListFaturaCartaoDados() {
        return Stream.of(buildMockFaturaCartaoDados()).collect(Collectors.toList());
    }

    public static Data buildMockData() {
        return Data.builder()
                .billId(TestConstants.fatura)
                .dueDate(TestConstants.due_date)
                .billTotalAmount(TestConstants.billTotalAmount)
                .financeCharges(buildMockListFinanceCharges())
                .build();
    }


    public static FinanceCharges buildMockFinanceCharges() {
        return FinanceCharges.builder()
                .type(TestConstants.type)
                .additionalInfo(TestConstants.additionalInfo)
                .amount(TestConstants.chargeAmount)
                .currency(TestConstants.chargeCurrency)
                .build();
    }

    public static List<FinanceCharges> buildMockListFinanceCharges() {
        return Stream.of(buildMockFinanceCharges()).collect(Collectors.toList());

    }

    public static Meta buildMockMeta() {
        return Meta.builder()
                .uuid(TestConstants.UUID)
                .sourceTimestamp(TestConstants.localDateTime)
                .cnpj(TestConstants.companyCnpj)
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
        arrayList.add(TestConstants.fatura);
        return arrayList;
    }

    public static FaturaCartaoEncargos buildMockFaturaCartaoEncargos() {
        return FaturaCartaoEncargos.builder()
                .row_num(TestConstants.row_num)
                .numConta(TestConstants.numConta)
                .billId(TestConstants.fatura)
                .type(TestConstants.encargoType)
                .currency(TestConstants.currency)
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
