package KNUChat.Record.repository;

import KNUChat.Record.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    List<Hashtag> findAllByRecordId(Long id);

    void deleteAllByRecordId(Long id);
}
