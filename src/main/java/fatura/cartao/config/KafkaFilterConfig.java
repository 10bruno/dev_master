package fatura.cartao.config;

import fatura.cartao.util.GsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
public class KafkaFilterConfig {

    private ConsumerFactory consumerFactory;
    private final static String PERMISSAO = "CREDIT_CARDS_ACCOUNTS_BILLS_READ";

    @Autowired
    public KafkaFilterConfig(ConsumerFactory consumerFactory) {
        this.consumerFactory = consumerFactory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> filtroFaturaCartao() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setAckDiscarded(true);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.setRecordFilterStrategy(this::deveFiltrar);
        return factory;
    }

    private Boolean deveFiltrar(ConsumerRecord<String, String> consumerRecord) {
        return !GsonDeserializer.getJson(consumerRecord.value()).getPermissao().toUpperCase().contains(PERMISSAO);
    }

}
