package fatura.cartao.service;

import fatura.cartao.config.FinanceChargesConfig;
import fatura.cartao.dto.EntradaJson;
import fatura.cartao.dto.FaturaCartao;
import fatura.cartao.entity.FaturaCartaoDados;
import fatura.cartao.entity.FaturaCartaoEncargos;
import fatura.cartao.repository.EncargosRepository;
import fatura.cartao.repository.FaturaRepository;
import fatura.cartao.util.MockBuilders;
import fatura.cartao.util.TestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FaturaCartaoServiceTest {

    @InjectMocks
    private FaturaCartaoService faturaCartaoService;

    @Mock
    private FaturaRepository faturaRepository;

    @Mock
    private EncargosRepository encargosRepository;

    @Mock
    private FinanceChargesConfig config;

    @Test
    public void deveProcessarFaturaERetornarListaDeFaturas() throws Exception {
        EntradaJson entradaJson = MockBuilders.buildMockEntradaJson();
        List<FaturaCartaoDados> faturaCartaoDadosList = MockBuilders.buildMockListFaturaCartaoDados();
        String json = MockBuilders.buildMockJson();
        ArrayList<String> faturas = MockBuilders.buildMockFaturas();
        List<FaturaCartaoEncargos> faturaCartaoEncargosList = MockBuilders.buildMockListFaturaCartaoEncargos();

        when(this.faturaRepository.findByNumConta(eq(entradaJson.getConta())))
                .thenReturn(Optional.ofNullable(faturaCartaoDadosList));
        when(this.encargosRepository.findByNumContaAndFaturasEncargos(eq(entradaJson.getConta()), eq(faturas)))
                .thenReturn(Optional.ofNullable(faturaCartaoEncargosList));

        List<FaturaCartao> faturaList = this.faturaCartaoService.processaFatura(json);
        Assertions.assertEquals(faturaList.get(0).getData().getBillId(), entradaJson.getFatura());
    }

    @Test
    public void deveProcessarFaturaERetornarSemEncargo() throws Exception {
        EntradaJson entradaJson = MockBuilders.buildMockEntradaJson();
        List<FaturaCartaoDados> faturaCartaoDadosList = MockBuilders.buildMockListFaturaCartaoDados();
        String json = MockBuilders.buildMockJson();
        ArrayList<String> faturas = MockBuilders.buildMockFaturas();
        List<FaturaCartaoEncargos> faturaCartaoEncargosList = new ArrayList<>();

        when(this.faturaRepository.findByNumConta(eq(entradaJson.getConta())))
                .thenReturn(Optional.ofNullable(faturaCartaoDadosList));
        when(this.encargosRepository.findByNumContaAndFaturasEncargos(eq(entradaJson.getConta()), eq(faturas)))
                .thenReturn(Optional.of(faturaCartaoEncargosList));

        List<FaturaCartao> faturaList = this.faturaCartaoService.processaFatura(json);
        Assertions.assertEquals(faturaList.get(0).getData().getFinanceCharges().get(0).getType(), TestConstants.semEncargo);
    }

    @Test
    public void deveProcessarFaturaERetornarChargesDiferenteDeOutros() throws Exception {
        EntradaJson entradaJson = MockBuilders.buildMockEntradaJson();
        List<FaturaCartaoDados> faturaCartaoDadosList = MockBuilders.buildMockListFaturaCartaoDados();
        String json = MockBuilders.buildMockJson();
        ArrayList<String> faturas = MockBuilders.buildMockFaturas();
        List<FaturaCartaoEncargos> faturaCartaoEncargosList = MockBuilders.buildMockListFaturaCartaoEncargos();
        faturaCartaoEncargosList.get(0).setType(TestConstants.typeIOF);

        when(this.faturaRepository.findByNumConta(eq(entradaJson.getConta())))
                .thenReturn(Optional.of(faturaCartaoDadosList));
        when(this.encargosRepository.findByNumContaAndFaturasEncargos(eq(entradaJson.getConta()), eq(faturas)))
                .thenReturn(Optional.of(faturaCartaoEncargosList));

        List<FaturaCartao> faturaList = this.faturaCartaoService.processaFatura(json);
        Assertions.assertEquals(faturaList.get(0).getData().getFinanceCharges().get(0).getType(), TestConstants.typeIOF);
    }

    @Test
    public void deveProcessarFaturaENaoEncontrarDados() {
        EntradaJson entradaJson = MockBuilders.buildMockEntradaJson();
        List<FaturaCartaoDados> faturaCartaoDadosList = new ArrayList<>();
        String json = MockBuilders.buildMockJson();

        when(this.faturaRepository.findByNumConta(eq(entradaJson.getConta())))
                .thenReturn(Optional.of(faturaCartaoDadosList));
        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> this.faturaCartaoService.processaFatura(json),
                "Nao localizou faturas para envio."
        );

        Assertions.assertTrue(exception.getMessage().contains("Nao localizou faturas para envio."));
    }

}