package KNUChat.Record.domain.repository;

import KNUChat.Record.domain.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    Page<Record> findByUserId(Long userId, Pageable pageable);

    Page<Record> findByHidingIsFalseAndUserId(Long userId, Pageable pageable);

    Page<Record> findByHidingIsFalseAndTitleContaining(String keyword, Pageable pageable);
}
