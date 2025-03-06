package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tuc.ds2020.entities.Client;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    /**
     * Example: JPA generate Query by Field
     */
    List<Client> findById(String name);
    // List<Device> findByAgeBefore(int age);
    /**
     * Example: Write Custom Query
     */
//    @Query(value = "SELECT p " +
//            "FROM Device p " +
//            "WHERE p.name = :name " +
//            "AND p.age >= 60  ")
//    Optional<Device> findSeniorsByName(@Param("name") String name);

}
