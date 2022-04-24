package site.minnan.mp.applicaiton.service.impl;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import site.minnan.mp.applicaiton.service.CharacterService;
import site.minnan.mp.domain.aggregate.Character;
import site.minnan.mp.domain.entity.CharacterInfo;
import site.minnan.mp.domain.entity.GraphData;
import site.minnan.mp.domain.repository.CharacterRepository;
import site.minnan.mp.infrastructure.enumerate.ArcaneType;
import site.minnan.mp.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.mp.infrastructure.utils.CharacterUtils;
import site.minnan.mp.infrastructure.utils.ExpUtils;
import site.minnan.mp.userinterface.dto.DetailsQueryDTO;
import site.minnan.mp.userinterface.dto.character.AddCharacterDTO;
import site.minnan.mp.userinterface.dto.character.QueryCharacterInfoDTO;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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

    @Autowired
    private ExpUtils expUtils;

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
    @Cacheable(cacheNames = "currentCharacter")
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

        return queryCharacterInfo(characterName);
    }


    /**
     * 切换角色
     *
     * @param dto
     */
    @Override
    @CacheEvict(value = "currentCharacter", allEntries = true)
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
            CharacterInfo characterInfo = queryCharacterInfo(character.getCharacterName());
            Integer currentLevel = characterInfo.getLevel();
            return EnumSet.allOf(ArcaneType.class).stream()
                    .filter(e -> currentLevel >= e.getMinLevel())
                    .sorted(Comparator.comparingInt(ArcaneType::getOrdinal))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }


    @Override
    public CharacterInfo queryCharacterInfo(String characterName) {
        CharacterInfo characterInfo = characterUtils.queryCharacterInfo(characterName);

        Integer nextStage = expUtils.getNextStage(characterInfo.getLevel());
        BigDecimal nextStageExp = expUtils.getNextStageExp(nextStage);
        List<GraphData> graphDataList = characterInfo.getGraphDataList();
        GraphData lastItem = graphDataList.get(graphDataList.size() - 2);
        BigDecimal expNeedToNextStage = nextStageExp.subtract(lastItem.getTotalOverallExp());

        characterInfo.setNextStageLevel(nextStage);
        if (!BigDecimal.ZERO.equals(lastItem.getExpDifference())) {
            BigDecimal dayToNextStageOne = expNeedToNextStage.divide(lastItem.getExpDifference(), 2,
                    RoundingMode.HALF_DOWN);
            if (dayToNextStageOne.compareTo(new BigDecimal(10000)) < 0) {
                characterInfo.setDayToNextStageFourteen(dayToNextStageOne);
                characterInfo.setDayToNextStageOne(dayToNextStageOne);
            }
        }


        BigDecimal expGainPastFourteen = graphDataList.stream().map(GraphData::getExpDifference).reduce(BigDecimal.ZERO,
                BigDecimal::add);
        if (!BigDecimal.ZERO.equals(expGainPastFourteen)) {
            BigDecimal dayToNextStageFourteen =
                    expNeedToNextStage.divide(expGainPastFourteen.divide(new BigDecimal(14), RoundingMode.HALF_UP),
                    2, RoundingMode.HALF_UP);
            if (dayToNextStageFourteen.compareTo(new BigDecimal(10000)) < 0) {
                characterInfo.setDayToNextStageFourteen(dayToNextStageFourteen);
            }
        }

        return characterInfo;
    }
}
