package unidue.ub.services.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import unidue.ub.services.gateway.model.Role;

import java.util.List;

/**
 * manages the available roles users can have
 */
public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(String name);

    @Query(value = "select * from role;",nativeQuery = true)
    List<Role> findAllDistinct();

}
