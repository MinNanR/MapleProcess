package site.minnan.mp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.minnan.mp.domain.aggregate.ArcaneAttainRecord;

public interface ArcaneAttainRecordRepository extends JpaRepository<ArcaneAttainRecord, Integer> {
}
