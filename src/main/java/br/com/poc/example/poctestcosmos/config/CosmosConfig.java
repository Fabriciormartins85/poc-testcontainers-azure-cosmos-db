package br.com.poc.example.poctestcosmos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosClientBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

//@Configuration
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class CosmosConfig {

    //@Bean
    static CosmosClientBuilder cosmosClientBuilder(
            @Value("${spring.cloud.azure.cosmos.endpoint}") String cosmosEndpoint,
            @Value("${spring.cloud.azure.cosmos.key}") String cosmosKey) {
        return new CosmosClientBuilder()
                .gatewayMode()
                .endpointDiscoveryEnabled(false)
                .endpoint(cosmosEndpoint)
                .key(cosmosKey);
    }

    //@Bean
    static CosmosAsyncClient cosmosAsyncClient(CosmosClientBuilder clientBuilder) {
        return clientBuilder
                .buildAsyncClient();
    }

}
