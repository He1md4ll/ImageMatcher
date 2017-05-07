package edu.hsbremen.cloud.persistance;

import edu.hsbremen.cloud.persistance.domain.ImageEntity;
import edu.hsbremen.cloud.persistance.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImageRepository extends CrudRepository<ImageEntity, Long> {
    List<ImageEntity> findByUsers(UserEntity user);
}