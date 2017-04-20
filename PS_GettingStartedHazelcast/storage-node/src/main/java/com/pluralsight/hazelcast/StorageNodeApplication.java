package com.pluralsight.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.SSLConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class StorageNodeApplication  {

    public static void main(String[] args) {
        SpringApplication.run(StorageNodeApplication.class, args);
    }

    @Bean(destroyMethod = "shutdown")
    public HazelcastInstance createStorageNode() throws Exception {
    	Config config = new Config();

    	config.setInstanceName("HazelcastService");
    	config.getNetworkConfig().setPortAutoIncrement(true);
    	config.getNetworkConfig().setPort(5701);
    	config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
    	config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
    	config.getNetworkConfig().getInterfaces().addInterface("127.0.0.1");
    	config.getNetworkConfig().getJoin().getTcpIpConfig().addMember("127.0.0.1");
    	config.getNetworkConfig().getInterfaces().setEnabled(true);

    	SSLConfig sslConfig = new SSLConfig();
    	sslConfig.setEnabled(false);
    	config.getNetworkConfig().setSSLConfig(sslConfig);

    	return Hazelcast.newHazelcastInstance(config);
    }

}
