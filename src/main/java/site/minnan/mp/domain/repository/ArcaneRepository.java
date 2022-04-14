package site.minnan.mp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.minnan.mp.domain.aggregate.Arcane;

import java.util.List;


public interface ArcaneRepository extends JpaRepository<Arcane, Integer> {

    /**
     * 根据角色id查找岛球信息
     *
     * @param characterId
     * @return
     */
    List<Arcane> findAllByCharacterIdIs(Integer characterId);
}
