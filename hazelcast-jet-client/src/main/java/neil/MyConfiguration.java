package neil;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;

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
            
	/**
	 * <P>Create a Jet client instance, using the IMDG client
	 * to connect to the Jet server cluster.
	 * 
	 * @param clientConfig Autoconfigured
	 * @return A Jet client instance
	 */
    @Bean
    public JetInstance jetInstance(ClientConfig clientConfig) { 
            return Jet.newJetClient(clientConfig);
    }

    /**
     * <P>Extract the IMDG client from inside the Jet client to make
     * visible as a @Bean
     * </P>
     * 
     * @param jetInstance Created manually, but autoconfigured would be simpler
     * @return The IMDG client instance inside the Jet client instance
     */
    @Bean
    public HazelcastInstance hazelcastInstance(JetInstance jetInstance) {
            return jetInstance.getHazelcastInstance();
    }

}
