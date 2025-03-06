package ro.tuc.ds2020.dtos;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class DeviceDetailsDTO {

    private Integer id;
    @NotNull
    private String description;
    @NotNull
    private String address;

    @NotNull
    private Integer consumption;

    public DeviceDetailsDTO() {
    }

    public DeviceDetailsDTO(String description, String address, Integer consumption) {
        this.description = description;
        this.address = address;
        this.consumption = consumption;
    }

    public DeviceDetailsDTO(Integer id, String description, String address, Integer consumption) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.consumption = consumption;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getConsumption() {
        return consumption;
    }

    public void setConsumption(Integer consumption) {
        this.consumption = consumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDetailsDTO that = (DeviceDetailsDTO) o;
        return Objects.equals(description, that.description) && Objects.equals(address, that.address) && (consumption == that.consumption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, address, consumption);
    }
}
