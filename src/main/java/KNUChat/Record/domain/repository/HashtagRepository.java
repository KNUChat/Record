package KNUChat.Record.domain.repository;

import KNUChat.Record.domain.entity.Hashtag;
import KNUChat.Record.domain.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    List<Hashtag> findAllByRecordId(Long id);

    @Query("SELECT r FROM Record r JOIN Hashtag h on h.record = r WHERE h.tag = :tag and r.hiding = false")
    Page<Record> findRecordByHidingIsFalseAndTag(@Param("tag") String tag, Pageable pageable);

    void deleteAllByRecordId(Long id);
}
