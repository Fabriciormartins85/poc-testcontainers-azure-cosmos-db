package br.com.poc.example.poctestcosmos.infra;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.time.Duration;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.CosmosDBEmulatorContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Testcontainers
@NoArgsConstructor
class AzureCosmosDbConfig implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Container
    private static CosmosDBEmulatorContainer emulator = new CosmosDBEmulatorContainer(
            DockerImageName.parse("mcr.microsoft.com/cosmosdb/linux/azure-cosmos-emulator:latest"))
            .withStartupTimeout(Duration.ofMinutes(3)).withEnv("AZURE_COSMOS_EMULATOR_PARTITION_COUNT", "4");

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        try {
            log.info("\n\n Try to start container");
            emulator.start();
            log.info("\n\n Container is started");
            log.info("Try to bind properties");
            var emulatorEndpoint = emulator.getEmulatorEndpoint();
            var emulatorKey = emulator.getEmulatorKey();
            createKeyStoreFromAzureCosmosAccess();
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext, "spring.cloud.azure.cosmos.endpoint=" + emulatorEndpoint,
                    "spring.cloud.azure.cosmos.key=" + emulatorKey);
            log.info("Finally bind properties");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void createKeyStoreFromAzureCosmosAccess() throws Exception {
        File keyStoreFile = File.createTempFile("azure-cosmos-emulator.keystore", "");
        keyStoreFile.mkdir();
        KeyStore keyStore = emulator.buildNewKeyStore();
        String emulatorKey = emulator.getEmulatorKey();
        keyStore.store(new FileOutputStream(keyStoreFile), emulatorKey.toCharArray());
        System.setProperty("javax.net.ssl.trustStore", keyStoreFile.toString());
        System.setProperty("javax.net.ssl.trustStorePassword", emulator.getEmulatorKey());
        System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
    }

}
