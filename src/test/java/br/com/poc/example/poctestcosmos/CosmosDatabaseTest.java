package br.com.poc.example.poctestcosmos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.models.CosmosContainerResponse;
import com.azure.cosmos.models.CosmosDatabaseResponse;

import br.com.poc.example.poctestcosmos.infra.IntegrationTestAzureCosmosDb;


@IntegrationTestAzureCosmosDb
public class CosmosDatabaseTest {

  @Autowired
	private CosmosAsyncClient client;

  @Test
  public void testCreationContainer() {
      CosmosDatabaseResponse databaseResponse = client.createDatabaseIfNotExists("Azure").block();
      assertEquals(201, databaseResponse.getStatusCode());
      CosmosContainerResponse containerResponse = client
      .getDatabase("Azure")
      .createContainerIfNotExists("ServiceContainer", "/name")
      .block();
      assertEquals(201, containerResponse.getStatusCode());

  }
}
