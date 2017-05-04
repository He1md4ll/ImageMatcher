package edu.hsbremen.cloud.persistance;

import edu.hsbremen.cloud.persistance.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}