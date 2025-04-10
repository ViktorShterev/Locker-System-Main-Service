package my.projects.lockersystemusermicroservice.repository;

import my.projects.lockersystemusermicroservice.entity.AccessCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessCodeRepository extends JpaRepository<AccessCode, Long> {
}
