package br.com.poc.example.poctestcosmos.domain.user;

import com.azure.spring.data.cosmos.repository.CosmosRepository;


public interface UserRepository extends CosmosRepository<User, String> {

}
