package site.minnan.mp;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.NumberUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import site.minnan.mp.domain.repository.CharacterRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest(classes = MapleProcessApplication.class)
public class CharacterTest {

    @Autowired
    CharacterRepository characterRepository;

    @Test
    public void testAddCharacter(){
//        Character character1 = new Character();
//        character1.setCharacterName("MinnanLum");
//        Optional<Character> queryOpt1 = characterRepository.findOne( Example.of(character1));
//        queryOpt1.ifPresent(e -> {
//            e.setJob("Luminous");
//            e.setLevel(251);
//            characterRepository.save(e);
//        });
//
//        Character character2 = new Character();
//        character2.setCharacterName("MinnanIL");
//        Optional<Character> queryOpt2 = characterRepository.findOne(Example.of(character2));
//        queryOpt2.ifPresent(e -> {
//            e.setJob("Ice/Lightning Archmage");
//            e.setLevel(230);
//            characterRepository.save(e);
//        });
        characterRepository.deleteByCharacterNameAfter("Dy");
    }
}
