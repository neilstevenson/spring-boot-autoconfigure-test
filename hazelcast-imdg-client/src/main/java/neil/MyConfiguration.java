package neil;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MyConfiguration {

    @Configuration
    @ConditionalOnMissingBean(ClientConfig.class)
    static class HazelcastClientConfigConfiguration {

        @Bean
        public ClientConfig clientConfig() throws Exception {
                log.warn("Creating 'ClientConfig' manually not autoconfigure");
                return new XmlClientConfigBuilder().build();
        }
    }
        
    @Configuration
    @ConditionalOnMissingBean(HazelcastInstance.class)
    static class HazelcastClientConfiguration {

        @Bean
        public HazelcastInstance hazelcastInstance(ClientConfig clientConfig) {
            log.warn("Creating client 'HazelcastInstance' manually not autoconfigure");
            return HazelcastClient.newHazelcastClient(clientConfig);
        }
    }
}
