package br.com.poc.example.poctestcosmos;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.azure.cosmos.CosmosAsyncClient;

import br.com.poc.example.poctestcosmos.infra.IntegrationTestAzureCosmosDb;

@IntegrationTestAzureCosmosDb
class PocTestCosmosApplicationTests {

	@Autowired
	private CosmosAsyncClient client;

	@Test
	void contextLoads() {
		assertNotNull(client, "Is not null");
	}

}
