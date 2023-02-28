package br.com.poc.example.poctestcosmos.user.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.poc.example.poctestcosmos.domain.user.User;
import br.com.poc.example.poctestcosmos.domain.user.UserRepository;
import br.com.poc.example.poctestcosmos.infra.LoadData;

@Component
class LoadDataUser implements LoadData {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void load() throws Exception {
        Resource resource = new ClassPathResource("data/users.json");
        List<User> users = mapper.readValue(resource.getFile(), new TypeReference<List<User>>() {
        });
        userRepository.saveAll(users);

    }

    @Override
    public String name() {
        return "Load All User For Test";
    }
}
