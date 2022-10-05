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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FaturaCartaoServiceTest {

    @InjectMocks
    private FaturaCartaoService faturaCartaoService;
    @Mock
    private FaturaRepository faturaRepository;
    @Mock
    private EncargosRepository encargosRepository;
    @Mock
    private FinanceChargesConfig config;

    @Test
    void deveProcessarFaturaERetornarListaDeFaturas() throws Exception {
        EntradaJson entradaJson = MockBuilders.buildMockEntradaJson();
        List<FaturaCartaoDados> faturaCartaoDadosList = MockBuilders.buildMockListFaturaCartaoDados();
        String json = MockBuilders.buildMockJson();
        ArrayList<String> faturas = MockBuilders.buildMockFaturas();
        List<FaturaCartaoEncargos> faturaCartaoEncargosList = MockBuilders.buildMockListFaturaCartaoEncargos();

        when(this.faturaRepository.findByNumConta(entradaJson.getConta()))
                .thenReturn(Optional.ofNullable(faturaCartaoDadosList));
        when(this.encargosRepository.findByNumContaAndFaturasEncargos(entradaJson.getConta(), faturas))
                .thenReturn(Optional.ofNullable(faturaCartaoEncargosList));

        List<FaturaCartao> faturaList = this.faturaCartaoService.processaFatura(json);
        Assertions.assertEquals(faturaList.get(0).getData().getBillId(), entradaJson.getFatura());
    }

    @Test
    void deveProcessarFaturaERetornarSemEncargo() throws Exception {
        EntradaJson entradaJson = MockBuilders.buildMockEntradaJson();
        List<FaturaCartaoDados> faturaCartaoDadosList = MockBuilders.buildMockListFaturaCartaoDados();
        String json = MockBuilders.buildMockJson();
        ArrayList<String> faturas = MockBuilders.buildMockFaturas();
        List<FaturaCartaoEncargos> faturaCartaoEncargosList = new ArrayList<>();

        when(this.faturaRepository.findByNumConta(entradaJson.getConta()))
                .thenReturn(Optional.ofNullable(faturaCartaoDadosList));
        when(this.encargosRepository.findByNumContaAndFaturasEncargos(entradaJson.getConta(), faturas))
                .thenReturn(Optional.of(faturaCartaoEncargosList));

        List<FaturaCartao> faturaList = this.faturaCartaoService.processaFatura(json);
        Assertions.assertEquals(TestConstants.SEM_ENCARGO, faturaList.get(0).getData().getFinanceCharges().get(0).getType());
    }

    @Test
    void deveProcessarFaturaERetornarChargesDiferenteDeOutros() throws Exception {
        EntradaJson entradaJson = MockBuilders.buildMockEntradaJson();
        List<FaturaCartaoDados> faturaCartaoDadosList = MockBuilders.buildMockListFaturaCartaoDados();
        String json = MockBuilders.buildMockJson();
        ArrayList<String> faturas = MockBuilders.buildMockFaturas();
        List<FaturaCartaoEncargos> faturaCartaoEncargosList = MockBuilders.buildMockListFaturaCartaoEncargos();
        faturaCartaoEncargosList.get(0).setType(TestConstants.TYPE_IOF);

        when(this.faturaRepository.findByNumConta(entradaJson.getConta()))
                .thenReturn(Optional.of(faturaCartaoDadosList));
        when(this.encargosRepository.findByNumContaAndFaturasEncargos(entradaJson.getConta(), faturas))
                .thenReturn(Optional.of(faturaCartaoEncargosList));

        List<FaturaCartao> faturaList = this.faturaCartaoService.processaFatura(json);
        Assertions.assertEquals(TestConstants.TYPE_IOF, faturaList.get(0).getData().getFinanceCharges().get(0).getType());
    }

    @Test
    void deveProcessarFaturaENaoEncontrarDados() {
        EntradaJson entradaJson = MockBuilders.buildMockEntradaJson();
        List<FaturaCartaoDados> faturaCartaoDadosList = new ArrayList<>();
        String json = MockBuilders.buildMockJson();

        when(this.faturaRepository.findByNumConta(entradaJson.getConta()))
                .thenReturn(Optional.of(faturaCartaoDadosList));
        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> this.faturaCartaoService.processaFatura(json),
                "Nao localizou faturas para envio."
        );

        Assertions.assertTrue(exception.getMessage().contains("Nao localizou faturas para envio."));
    }

}