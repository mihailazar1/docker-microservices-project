package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class DeviceDTO extends RepresentationModel<DeviceDTO> {
    private Integer id;
    private String description;
    private Integer consumption;

    public DeviceDTO() {
    }

    public DeviceDTO(Integer id, String description, Integer consumption) {
        this.id = id;
        this.description = description;
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
        DeviceDTO deviceDTO = (DeviceDTO) o;
        return (consumption == deviceDTO.consumption) &&
                Objects.equals(description, deviceDTO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, consumption);
    }
}
