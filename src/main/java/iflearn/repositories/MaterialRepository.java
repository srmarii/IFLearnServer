package iflearn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import iflearn.entities.*;

public interface MaterialRepository extends JpaRepository<Material, Integer>{

}
