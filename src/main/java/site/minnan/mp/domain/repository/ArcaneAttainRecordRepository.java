package site.minnan.mp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import site.minnan.mp.domain.aggregate.ArcaneAttainRecord;

public interface ArcaneAttainRecordRepository extends JpaRepository<ArcaneAttainRecord, Integer>,
        JpaSpecificationExecutor<ArcaneAttainRecord> {

}
