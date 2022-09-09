package fatura.cartao.service;

import fatura.cartao.config.FaturaCartaoConfig;
import fatura.cartao.dto.FaturaCartao;
import fatura.cartao.util.MockBuilders;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProducerServiceTest {

    @InjectMocks
    private ProducerService producerService;
    @Mock
    private FaturaCartaoConfig config;
    @Mock
    private KafkaTemplate<String, FaturaCartao> kafkaTemplate;
    @Mock
    private ListenableFuture<SendResult<String, FaturaCartao>> result;

    @Test
    void deveProduzirMensagemERetornarSucesso() {
        FaturaCartao faturaCartao = MockBuilders.buildMockFaturaCartao();
        List<FaturaCartao> faturaCartaoList = MockBuilders.buildMockListFaturaCartao();
        String json = MockBuilders.buildMockJson();
        when(config.getProducer()).thenReturn("sucesso");
        SendResult sendResult = mock(SendResult.class);
        RecordMetadata recordMetadata = new RecordMetadata(new TopicPartition(config.getProducer(), 1), 1L, 5, 5, 2, 2);
        ProducerRecord<String, FaturaCartao> producerRecord = new ProducerRecord<>(config.getProducer(), faturaCartao);
        given(sendResult.getRecordMetadata()).willReturn(recordMetadata);
        given(sendResult.getProducerRecord()).willReturn(producerRecord);
        when(kafkaTemplate.send(anyString(), any(FaturaCartao.class))).thenReturn(result);

        doAnswer(invocationOnMock -> {
            ListenableFutureCallback<Object> listenableFutureCallback = invocationOnMock.getArgument(0);
            listenableFutureCallback.onSuccess(sendResult);
            Assertions.assertNotNull(sendResult.getProducerRecord().value());
            return null;
        }).when(result).addCallback(any(ListenableFutureCallback.class));

        producerService.send(faturaCartaoList, json);
        verify(kafkaTemplate, times(1)).send(anyString(), any(FaturaCartao.class));
    }

    @Test
    void deveProduzirMensagemERetornarErro() {
        List<FaturaCartao> faturaCartaoList = MockBuilders.buildMockListFaturaCartao();
        String json = MockBuilders.buildMockJson();
        when(config.getProducer()).thenReturn("sucesso");
        Throwable throwable = mock(Throwable.class);
        when(kafkaTemplate.send(anyString(), any(FaturaCartao.class))).thenReturn(result);

        doAnswer(invocationOnMock -> {
            ListenableFutureCallback<Object> listenableFutureCallback = invocationOnMock.getArgument(0);
            listenableFutureCallback.onFailure(throwable);
            return null;
        }).when(result).addCallback(any(ListenableFutureCallback.class));

        producerService.send(faturaCartaoList, json);
        verify(kafkaTemplate, times(1)).send(anyString(), any(FaturaCartao.class));
    }
}