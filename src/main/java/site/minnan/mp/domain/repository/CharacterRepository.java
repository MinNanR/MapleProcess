package site.minnan.mp.domain.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.minnan.mp.domain.aggregate.Character;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Integer> {

    /**
     * 根据使用状态查找角色
     *
     * @param current
     * @return
     */
    Character findOneByCurrent(Integer current);

    void deleteByCharacterNameAfter(String characterName);

    default Optional<Character> findCurrent() {
        return findOne(Example.of(Character.ofCurrent()));
    }
}
