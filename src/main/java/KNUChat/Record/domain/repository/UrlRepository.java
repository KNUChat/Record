package KNUChat.Record.domain.repository;

import KNUChat.Record.domain.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    List<Url> findAllByRecordId(Long id);

    void deleteAllByRecordId(Long id);
}
