package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2020.entities.Device;
import java.util.Optional;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Integer> {

    /**
     * Example: JPA generate Query by Field
     */
    List<Device> findByDescription(String name);
    // List<Device> findByAgeBefore(int age);
    /**
     * Example: Write Custom Query
     */
//    @Query(value = "SELECT p " +
//            "FROM Device p " +
//            "WHERE p.name = :name " +
//            "AND p.age >= 60  ")
//    Optional<Device> findSeniorsByName(@Param("name") String name);

       @Query(value = "SELECT p " +
            "FROM Device p " +
            "WHERE p.clientid = :clientId")
      List<Device> findByClientId(@Param("clientId") Integer clientId);

}
