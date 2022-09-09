package fatura.cartao.service;

import fatura.cartao.dto.FaturaCartao;
import fatura.cartao.util.MockBuilders;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsumerServiceTest {

    @InjectMocks
    private ConsumerService consumerService;
    @Mock
    private ProducerService producerService;
    @Mock
    private FaturaCartaoService faturaCartaoService;

    @Test
    void deveConsumirMensagemCadastroComFaturaERetornarSucesso() throws Exception {
        List<FaturaCartao> faturaCartaoList = MockBuilders.buildMockListFaturaCartao();
        ConsumerRecord<String, String> consumerRecord = MockBuilders.buildConsumerRecord();
        when(this.faturaCartaoService.processaFatura(any())).thenReturn(faturaCartaoList);
        Acknowledgment ack = mock(Acknowledgment.class);

        this.consumerService.listener(consumerRecord, ack);
        verify(ack, times(1)).acknowledge();
    }

    @Test
    void deveTentarConsumirERetornarException() throws Exception {
        String json = MockBuilders.buildMockJson();
        List<FaturaCartao> faturaCartaoList = MockBuilders.buildMockListFaturaCartao();
        ConsumerRecord<String, String> consumerRecord = MockBuilders.buildConsumerRecord();
        doThrow(Exception.class)
                .when(this.faturaCartaoService)
                .processaFatura(any());
        Acknowledgment ack = mock(Acknowledgment.class);

        this.consumerService.listener(consumerRecord, ack);
        verify(producerService, times(0)).send(faturaCartaoList, json);
    }
}