package ro.tuc.ds2020.entities;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Mdevice implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "deviceid", nullable = false)
    private Integer deviceid;

    @Column(name = "mhec", nullable = false)
    private Integer mhec;

//
//    @ManyToOne
//    private Client clientid;

    public Mdevice() {
    }

    public Mdevice(Integer deviceid, Integer mhec) {
        this.deviceid = deviceid;
        this.mhec = mhec;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Integer deviceid) {
        this.deviceid = deviceid;
    }

    public Integer getMhec() {
        return mhec;
    }

    public void setMhec(Integer mhec) {
        this.mhec = mhec;
    }
}
