package KNUChat.Record.repository;

import KNUChat.Record.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    Page<Record> findByUserId(Long userId, Pageable pageable);

    Page<Record> findByTitleContaining(String keyword, Pageable pageable);
}
