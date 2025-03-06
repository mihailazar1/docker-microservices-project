package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class ClientDTO extends RepresentationModel<ClientDTO> {
    private Integer id;
    private String name;
    private String role;


    public ClientDTO() {
    }

    public ClientDTO(Integer id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return Objects.equals(name, clientDTO.name) && Objects.equals(role, clientDTO.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, role);
    }
}
