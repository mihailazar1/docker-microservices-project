package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.ClientDTO;
import ro.tuc.ds2020.dtos.ClientDetailsDTO;
import ro.tuc.ds2020.entities.Client;

public class clientBuilder {

    private clientBuilder() {
    }

    public static ClientDTO toClientDTO(Client client) {
        return new ClientDTO(client.getId(), client.getName(), client.getRole());
    }

    public static ClientDetailsDTO toClientDetailsDTO(Client client) {
        return new ClientDetailsDTO(client.getId(), client.getName(), client.getPassword(), client.getRole());
    }

    public static Client toEntity(ClientDetailsDTO clientDetailsDTO) {
        return new Client(clientDetailsDTO.getName(), clientDetailsDTO.getPassword(),
                clientDetailsDTO.getRole());
    }
}
