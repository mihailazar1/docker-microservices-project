package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.ClientDTO;
import ro.tuc.ds2020.dtos.ClientDetailsDTO;
import ro.tuc.ds2020.dtos.builders.clientBuilder;
import ro.tuc.ds2020.entities.Client;
import ro.tuc.ds2020.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public ClientService(ClientRepository clientRepository, RestTemplate restTemplate) {
        this.clientRepository = clientRepository;
        this.restTemplate = restTemplate;
    }

    public List<ClientDTO> findClients() {
        List<Client> clientList = clientRepository.findAll();
        return clientList.stream()
                .map(clientBuilder::toClientDTO)
                .collect(Collectors.toList());
    }

    public ClientDetailsDTO findClientById(Integer id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (!clientOptional.isPresent()) {
            LOGGER.error("Client with id {} was not found in db", id);
            throw new ResourceNotFoundException(Client.class.getSimpleName() + " with id: " + id);
        }
        return clientBuilder.toClientDetailsDTO(clientOptional.get());
    }

    public Integer insert(ClientDetailsDTO clientDTO) {
        Client client = clientBuilder.toEntity(clientDTO);
        client = clientRepository.save(client);
        LOGGER.debug("Client with id {} was inserted in db", client.getId());

        try {
            String microserviceUrl = "http://localhost:8081/device/client/";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");  // If needed
            Integer clientId = client.getId();

            microserviceUrl = microserviceUrl + Integer.toString(clientId);
            // System.out.println(microserviceUrl);
            // Create request entity (if you need to send anything else with the ID)
            HttpEntity<Integer> request = new HttpEntity<>(clientId, headers);

            // Make the POST request to the external microservice
            ResponseEntity<String> response = restTemplate.exchange(microserviceUrl, HttpMethod.POST, request, String.class);

            // Log the response or handle accordingly
            LOGGER.debug("Response from microservice: {}", response.getBody());
        } catch (Exception e) {
            LOGGER.error("Error calling microservice: {}", e.getMessage());
            // Handle the error as per your requirement (e.g., rethrow, log, etc.)
        }

        return client.getId();
    }

    public boolean deleteClientById(Integer clientId) {
        if(clientRepository.existsById(clientId)){
            clientRepository.deleteById(clientId);
            LOGGER.debug("Client with id {} was deleted from client-db", clientId);
            return true;
        } else {
            LOGGER.debug("There is NO client with id {} in client-db", clientId);
            return false;
        }

    }

    public Integer update(Integer clientID, ClientDetailsDTO clientDTO) {
        Client client = clientBuilder.toEntity(clientDTO);
        Optional<Client> clientOptional = clientRepository.findById(clientID);
        if (!clientOptional.isPresent()) {
            LOGGER.error("Cannot update. Client with id {} was not found in db", clientID);
            throw new ResourceNotFoundException(Client.class.getSimpleName() + " with id: " + clientID);
        }

        clientOptional.get().setName(client.getName());
        clientOptional.get().setRole(client.getRole());


        if(client.getPassword() != null)
            clientOptional.get().setPassword(client.getPassword());

        client = clientRepository.save(clientOptional.get());
        return client.getId();

    }

    public ClientDTO authenticate(String name, String password) {
        List<Client> clientList = clientRepository.findByName(name);


        if(!clientList.isEmpty()){ // suppose names are unique
            Client client = clientList.get(0);

            if(client.getPassword().equals(password)){
               return new ClientDTO(client.getId(), client.getName(), client.getRole());

            } else {
                System.out.println("Auth failed, wrong password");

            }


        } else {
            System.out.println("Auth failed, no user found with the name:" + name);

        }
        return null;

    }
}
