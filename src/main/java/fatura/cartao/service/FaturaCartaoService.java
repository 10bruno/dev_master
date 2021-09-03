package fatura.cartao.service;

import fatura.cartao.config.FinanceChargesConfig;
import fatura.cartao.dto.*;
import fatura.cartao.entity.FaturaCartaoDados;
import fatura.cartao.entity.FaturaCartaoEncargos;
import fatura.cartao.repository.EncargosRepository;
import fatura.cartao.repository.FaturaRepository;
import fatura.cartao.util.GsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FaturaCartaoService {

    private final FaturaRepository faturaRepository;
    private final EncargosRepository encargosRepository;
    private final FinanceChargesConfig financeChargesConfig;
    private static final String OUTROS = "OUTROS";
    private static final String SEM_ENCARGO = "SEM_ENCARGO";
    private static final String MOEDA = "BRL";
    private static final String SEM_FATURAS = "Nao localizou faturas para envio.";

    @Autowired
    public FaturaCartaoService(FaturaRepository faturaRepository, EncargosRepository encargosRepository, FinanceChargesConfig financeChargesConfig) {
        this.faturaRepository = faturaRepository;
        this.encargosRepository = encargosRepository;
        this.financeChargesConfig = financeChargesConfig;
    }

    public List<FaturaCartao> processaFatura(String json) throws Exception {
        EntradaJson entradaJson = getGson(json);
        List<FaturaCartao> faturaCartaoList = new ArrayList<>();
        List<FaturaCartaoDados> dadosFatura = findDadosFatura(entradaJson);

        if (!dadosFatura.isEmpty()) {
            faturaCartaoList = buildFatura(entradaJson.getConta(), dadosFatura);
        }
        if (faturaCartaoList.isEmpty()) {
            throw new Exception(SEM_FATURAS);
        }
        return faturaCartaoList;
    }

    private EntradaJson getGson(String json) {
        return GsonDeserializer.getJson(json);
    }

    private List<FaturaCartaoDados> findDadosFatura(EntradaJson entradaJson) {
        return this.faturaRepository.findByNumConta(entradaJson.getConta()).orElse(List.of());
    }

    private List<FaturaCartao> buildFatura(String conta, List<FaturaCartaoDados> faturaCartaoDadosList) {
        List<FaturaCartao> faturaCartaoList = new ArrayList<>();
        List<FaturaCartaoEncargos> encargosList = findEncargos(conta, getNumFaturaList(faturaCartaoDadosList));

        for (FaturaCartaoDados faturaCartaoDados : faturaCartaoDadosList) {
            FaturaCartao faturaCartao = new FaturaCartao();
            Data returnData = getData(faturaCartaoDados);
            returnData.setFinanceCharges(getFinanceCharges(faturaCartaoDados.getBillId(), encargosList));
            faturaCartao.setData(returnData);
            faturaCartao.setMeta(getMeta(faturaCartaoDados));
            faturaCartaoList.add(faturaCartao);
        }
        return faturaCartaoList;
    }

    private List<String> getNumFaturaList(List<FaturaCartaoDados> faturaCartaoDadosList) {
        return faturaCartaoDadosList.stream().distinct().map(FaturaCartaoDados::getBillId).collect(Collectors.toList());
    }

    private List<FaturaCartaoEncargos> findEncargos(String numConta, List<String> faturas) {
        return this.encargosRepository.findByNumContaAndFaturasEncargos(numConta, faturas).orElse(List.of());
    }

    private Data getData(FaturaCartaoDados faturaCartaoDados) {
        return Data.builder()
                .billId(faturaCartaoDados.getBillId())
                .dueDate(faturaCartaoDados.getDue_date())
                .billTotalAmount(faturaCartaoDados.getBillTotalAmount())
                .build();
    }

    private List<FinanceCharges> getFinanceCharges(String billId, List<FaturaCartaoEncargos> faturaCartaoEncargosList) {
        return faturaCartaoEncargosList
                .stream()
                .filter(faturaCartaoEncargos -> faturaCartaoEncargos.getBillId().equals(billId))
                .map(this::chargesMapper)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(), e -> !e.isEmpty() ? e : List.of(noChargesMapper())
                ));
    }

    private FinanceCharges chargesMapper(FaturaCartaoEncargos encargo) {
        return FinanceCharges.builder()
                .type(getChargeValueType(encargo.getType()))
                .additionalInfo(getChargesAdditionalInfo(encargo.getType()))
                .amount(encargo.getAmount())
                .currency(encargo.getCurrency())
                .build();
    }

    private String getChargeValueType(String valueType) {
        return valueType.startsWith(OUTROS) ? OUTROS : valueType;
    }

    private String getChargesAdditionalInfo(String valueType) {
        return valueType.startsWith(OUTROS) ?
                financeChargesConfig.getTipos()
                        .stream()
                        .filter(tipos -> tipos.getNome().equals(valueType))
                        .findFirst()
                        .map(FinanceChargesConfig.Tipos::getDescricao)
                        .orElse(valueType) : null;
    }

    private FinanceCharges noChargesMapper() {
        return FinanceCharges.builder()
                .type(SEM_ENCARGO)
                .amount(BigDecimal.ZERO)
                .currency(MOEDA)
                .build();
    }

    private Meta getMeta(FaturaCartaoDados faturaCartaoDados) {
        return Meta.builder()
                .uuid(UUID.randomUUID().toString())
                .sourceTimestamp(LocalDateTime.now())
                .cnpj(faturaCartaoDados.getCnpj())
                .build();
    }

}
