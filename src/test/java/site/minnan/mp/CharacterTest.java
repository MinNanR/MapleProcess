package site.minnan.mp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.minnan.mp.domain.aggregate.Character;
import site.minnan.mp.domain.repository.CharacterRepository;

import java.util.Iterator;
import java.util.stream.Stream;

@SpringBootTest(classes = MapleProcessApplication.class)
public class CharacterTest {

    @Autowired
    CharacterRepository characterRepository;

    @Test
    public void testAddCharacter(){
        Character character = new Character();
        character.setId(1);
        character.setName("min");
        characterRepository.save(character);
    }
}
