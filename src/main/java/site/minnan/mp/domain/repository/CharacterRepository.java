package site.minnan.mp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.minnan.mp.domain.aggregate.Character;

public interface CharacterRepository extends JpaRepository<Character, Integer> {

}
