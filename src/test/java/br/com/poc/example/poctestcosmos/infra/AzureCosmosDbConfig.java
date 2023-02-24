package br.com.poc.example.poctestcosmos.infra;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.util.function.Supplier;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.CosmosDBEmulatorContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import lombok.NoArgsConstructor;

@Testcontainers
@NoArgsConstructor
class AzureCosmosDbConfig implements  ApplicationContextInitializer<ConfigurableApplicationContext>{

    @Container
    private static CosmosDBEmulatorContainer emulator = new CosmosDBEmulatorContainer(
            DockerImageName.parse("mcr.microsoft.com/cosmosdb/linux/azure-cosmos-emulator:latest"));

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        try {
            Supplier<Object> emulatorEndpoint = () -> String.valueOf(emulator.getEmulatorEndpoint());
            Supplier<Object> semulatorKey = () -> String.valueOf(emulator.getEmulatorKey());
            System.out.println("Container");
            System.out.println(emulatorEndpoint.get());
            System.out.println(semulatorKey.get());
            registry.add("spring.cloud.azure.cosmos.endpoint", emulatorEndpoint);
            registry.add("spring.cloud.azure.cosmos.key", semulatorKey);
            System.out.println("Criando emulador");
    
            System.out.println("Criado emulador");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if(emulator.isCreated() && emulator.isRunning()){
            return;
        }
        emulator.start();
        var emulatorEndpoint = emulator.getEmulatorEndpoint();
        var emulatorKey = emulator.getEmulatorKey();
        try{
        createKeyStoreFromAzureCosmosAccess();
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
            applicationContext, "spring.cloud.azure.cosmos.endpoint="+ emulatorEndpoint, 
            "spring.cloud.azure.cosmos.key="+emulatorKey);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void createKeyStoreFromAzureCosmosAccess() throws Exception{
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
