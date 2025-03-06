package ro.tuc.ds2020.entities;


import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Client implements Serializable {
    @Id
    private Integer id;

    @OneToMany(cascade=CascadeType.ALL)
    @Column(name = "devices", nullable = true)
    private List<Device> devices;

    public Client(){
    }
    public Client(int id, List<Device> devices){
        this.id = id;
        this.devices = devices;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
