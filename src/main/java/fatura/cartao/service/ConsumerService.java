package fatura.cartao.service;

import fatura.cartao.dto.FaturaCartao;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ConsumerService {

    private final ProducerService producerService;
    private final FaturaCartaoService faturaCartaoService;

    @Autowired
    public ConsumerService(ProducerService producerService, FaturaCartaoService faturaCartaoService) {
        this.producerService = producerService;
        this.faturaCartaoService = faturaCartaoService;
    }

    @KafkaListener(topics = "#{@faturaCartaoConfig.getConsumer()}", groupId = "fatura-cartao", containerFactory = "filtroFaturaCartao")
    public void listener(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String json = record.value();
        try {
            log.info("Processando mensagem -> {}", json);
            List<FaturaCartao> faturaCartao = this.faturaCartaoService.processaFatura(json);
            this.producerService.send(faturaCartao, json);
        } catch (Exception e) {
            log.error("Erro inesperado " + e.getMessage());
        } finally {
            ack.acknowledge();
        }
    }
}
