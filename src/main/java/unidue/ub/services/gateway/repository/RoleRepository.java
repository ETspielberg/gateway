package unidue.ub.services.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import unidue.ub.services.gateway.model.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {

    public Role findByName(String name);

    @Query(value = "select * from role;",nativeQuery = true)
    public List<Role> findAllDistinct();

}
