package br.com.poc.example.poctestcosmos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.config.CosmosConfig;
import com.azure.spring.data.cosmos.core.CosmosTemplate;
import com.azure.spring.data.cosmos.core.convert.MappingCosmosConverter;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;

@Configuration
@EnableCosmosRepositories(basePackages = "br.com.poc.example.poctestcosmos.domain.user", cosmosTemplateRef = "databaseUserTemplate")
class UserDatabaseConfiguration extends AbstractCosmosConfiguration {

    private static final String USER_DB_NAME = "Users";

    @Bean
    public CosmosTemplate databaseUserTemplate(CosmosAsyncClient client, CosmosConfig cosmosConfig, MappingCosmosConverter mappingCosmosConverter) {
        return new CosmosTemplate(client, USER_DB_NAME , cosmosConfig, mappingCosmosConverter);
    }

    @Override
    protected String getDatabaseName() {
        return USER_DB_NAME;
    }
}
