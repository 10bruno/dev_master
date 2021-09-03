package fatura.cartao.service;

import fatura.cartao.config.FaturaCartaoConfig;
import fatura.cartao.dto.FaturaCartao;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;

@Service
@Slf4j
public class ProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final FaturaCartaoConfig faturaCartaoConfig;

    @Autowired
    public ProducerService(KafkaTemplate<String, Object> kafkaTemplate, FaturaCartaoConfig faturaCartaoConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.faturaCartaoConfig = faturaCartaoConfig;
    }

    public void send(List<FaturaCartao> faturaCartaoList, String json) {

        try {
            for (FaturaCartao faturaCartao : faturaCartaoList) {
                ListenableFuture<SendResult<String, Object>> result = kafkaTemplate.send(faturaCartaoConfig.getProducer(), faturaCartao);
                result.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                    @Override
                    public void onSuccess(SendResult<String, Object> result) {
                        log.info("Mensagem enviada -> {}", result.getProducerRecord().value());
                        log.info("Tópico -> {}", result.getRecordMetadata().topic());
                    }

                    @SneakyThrows
                    @Override
                    public void onFailure(Throwable ex) {
                        throw new Throwable("Erro ao enviar mensagem para o tópico -> " + faturaCartaoConfig.getProducer());
                    }
                });
            }
        } catch (Throwable ex) {
            log.error("Enviando mensagem para o topico de erro -> " + json);
            kafkaTemplate.send(faturaCartaoConfig.getError(), json);
        }
    }
}