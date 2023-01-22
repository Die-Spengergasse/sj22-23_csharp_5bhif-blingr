package at.spengergasse.mate.repository;

import at.spengergasse.mate.domain.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UploadRepository extends JpaRepository<Upload, Long> {
}
