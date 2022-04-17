package site.minnan.mp.applicaiton.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import site.minnan.mp.infrastructure.enumerate.ArcaneType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import site.minnan.mp.applicaiton.service.CharacterService;
import site.minnan.mp.domain.aggregate.Character;
import site.minnan.mp.domain.entity.CharacterInfo;
import site.minnan.mp.domain.repository.CharacterRepository;
import site.minnan.mp.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.mp.infrastructure.exception.EntityNotExistException;
import site.minnan.mp.infrastructure.utils.CharacterUtils;
import site.minnan.mp.userinterface.dto.character.AddCharacterDTO;
import site.minnan.mp.userinterface.dto.DetailsQueryDTO;
import site.minnan.mp.userinterface.dto.character.QueryCharacterInfoDTO;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色service
 *
 * @author Minnan on 2022/03/30
 */
@Service("characterService")
@Slf4j
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    private CharacterRepository characterRepository;


    @Autowired
    @Qualifier(value = "cache")
    private CacheManager caffeine;

    @Autowired
    private CharacterUtils characterUtils;

    /**
     * 查询角色列表
     */
    @Override
    public List<Character> getCharacterList() {
        return characterRepository.findAll();
    }

    /**
     * 获取当前查看的角色
     *
     * @return
     */
    @Override
    public Character getCurrentCharacter() {
        Optional<Character> opt = characterRepository.findOne(Example.of(Character.ofCurrent()));
        return opt.orElseGet(Character::new);
    }

    /**
     * 添加角色
     *
     * @param dto
     */
    @Override
    public void addCharacter(AddCharacterDTO dto) {
        Character character = new Character();
        character.setCharacterName(dto.getCharacterName());
        character.setLevel(dto.getLevel());
        character.setJob(dto.getJob());
        characterRepository.save(character);
    }

    /**
     * 远程查询角色信息
     *
     * @param dto
     * @return
     */
    @Override
    public CharacterInfo getCharacterInfo(QueryCharacterInfoDTO dto) {
        String characterName = dto.getCharacterName();
        Character character = new Character();
        character.setCharacterName(characterName);
        Optional<Character> check = characterRepository.findOne(Example.of(character));
        if (check.isPresent()) {
            throw new EntityAlreadyExistException("角色已存在");
        }

        JSONObject characterData = queryCharacterInfo(characterName);

        return new CharacterInfo(characterData);
    }


    /**
     * 切换角色
     *
     * @param dto
     */
    @Override
    public void switchCharacter(DetailsQueryDTO dto) {
        List<Character> allCharacter = characterRepository.findAll();
        allCharacter.forEach(e -> e.setCurrent(0));
        characterRepository.saveAll(allCharacter);

        Optional<Character> current = characterRepository.findById(dto.getId());
        current.ifPresent(e -> {
            e.setCurrent(1);
            characterRepository.save(e);
        });
    }

    /**
     * 获取当前角色岛球列表
     */
    @Override
    public List<ArcaneType> getCurrentCharacterArcType() {
        Optional<Character> currentCharacter = characterRepository.findOne(Example.of(Character.ofCurrent()));
        if (currentCharacter.isPresent()) {
            Character character = currentCharacter.get();
            JSONObject characterData = queryCharacterInfo(character.getCharacterName());
            Integer currentLevel = characterData.getInt("Level");
            return EnumSet.allOf(ArcaneType.class).stream()
                    .filter(e -> currentLevel >= e.getMinLevel())
                    .sorted(Comparator.comparingInt(ArcaneType::getOrdinal))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }


    @Override
    public JSONObject queryCharacterInfo(String characterName){
        return characterUtils.queryCharacterInfo(characterName);
    }
}
