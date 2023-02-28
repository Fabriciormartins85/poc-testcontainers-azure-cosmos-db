package br.com.poc.example.poctestcosmos.domain.user;

import org.springframework.data.annotation.Id;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Container(containerName = "users", autoCreateContainer = true)
@AllArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue
    private String id;

    @PartitionKey
    private String name;

}
