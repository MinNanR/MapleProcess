package site.minnan.mp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.minnan.mp.domain.aggregate.Arcane;


public interface ArcaneRepository extends JpaRepository<Arcane, Integer> {
}
