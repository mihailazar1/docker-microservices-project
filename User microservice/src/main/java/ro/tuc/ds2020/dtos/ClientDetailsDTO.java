package ro.tuc.ds2020.dtos;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ClientDetailsDTO {

    private Integer id;
    @NotNull
    private String name;

    @Nullable
    private String password;
    @NotNull
    private String role;

    public ClientDetailsDTO() {
    }

    public ClientDetailsDTO(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public ClientDetailsDTO(Integer id, String name, String password, String role) {
        this.id = id;
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        ClientDetailsDTO that = (ClientDetailsDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(role, that.role) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, role);
    }
}
