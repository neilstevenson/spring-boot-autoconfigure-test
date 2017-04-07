package neil;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.config.JetConfig;

@Configuration
public class MyConfiguration {

	/**
	 * <P>Create IMDG server config to stop autoconfiguration
	 * doing the wrong thing.
	 * </P>
	 * 
	 * @return IMDG server config
	 */
    @Bean
    public Config config() {
            return new ClasspathXmlConfig("hazelcast.xml");
    }

    /**
     * <P>Create Jet server instance, that internally builds
     * an IMDG server instance.
     * </P>
     * 
     * @param config Created manually but autoconfigured would be simpler
     * @return A Jet server instance @Bean
     */
    @Bean
    public JetInstance jetInstance(Config config) {
            JetConfig jetConfig = new JetConfig();
            
            jetConfig.setHazelcastConfig(config);
            
            return Jet.newJetInstance(jetConfig);
    }

    /**
     * <P>Extract the embedded IMDG server instance from the Jet server
     * instance to make it visible as a @Bean, for demonstration purposes.
     * </P>
     * 
     * @param jetInstance Created manually but autoconfigured would be simpler
     * @return The IMDG server instance inside that Jet server instance
     */
    @Bean
    public HazelcastInstance hazelcastInstance(JetInstance jetInstance) {
            return jetInstance.getHazelcastInstance();
    }
}
