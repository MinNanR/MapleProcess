package site.minnan.mp.applicaiton.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import javafx.scene.shape.Arc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.minnan.mp.applicaiton.service.ArcaneService;
import site.minnan.mp.applicaiton.service.CharacterService;
import site.minnan.mp.domain.aggregate.Arcane;
import site.minnan.mp.domain.aggregate.ArcaneAttainRecord;
import site.minnan.mp.domain.aggregate.Character;
import site.minnan.mp.domain.repository.ArcaneAttainRecordRepository;
import site.minnan.mp.domain.repository.ArcaneRepository;
import site.minnan.mp.domain.repository.CharacterRepository;
import site.minnan.mp.infrastructure.enumerate.ArcaneType;
import site.minnan.mp.infrastructure.exception.EntityNotExistException;
import site.minnan.mp.userinterface.dto.arcane.AddAttainRecordDTO;
import site.minnan.mp.userinterface.dto.arcane.AttainItem;
import site.minnan.mp.userinterface.dto.arcane.InitArcaneItem;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 岛球服务
 *
 * @author Minnan on 2022/04/13
 */
@Service
public class ArcaneServiceImpl implements ArcaneService {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private ArcaneRepository arcaneRepository;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private ArcaneAttainRecordRepository recordRepository;

    /**
     * 添加或更新岛球获取记录
     *
     * @param dto
     */
    @Override
    @Transactional
    public void addOrUpdateArcaneAttainRecord(AddAttainRecordDTO dto) {
        List<AttainItem> attainList = dto.getAttainList();
        Optional<Character> characterOpt = characterRepository.findCurrent();
        if (!characterOpt.isPresent()) {
            throw new EntityNotExistException("未指定当前角色");
        }

        Character character = characterOpt.get();
        Integer characterId = character.getId();

        Arcane arcQuery = new Arcane();
        arcQuery.setCharacterId(characterId);
        List<Arcane> arcaneList = arcaneRepository.findAll(Example.of(arcQuery));
        Map<ArcaneType, Arcane> arcaneMap = arcaneList.stream().collect(Collectors.toMap(e -> e.getArcaneType(),
                e -> e));

        ArcaneAttainRecord recordQuery = new ArcaneAttainRecord();
        DateTime noteDate = DateUtil.parse(dto.getNoteDate(), "yyyy-MM-dd");
        recordQuery.setCharacterId(characterId);
        recordQuery.setNoteDate(noteDate);
        List<ArcaneAttainRecord> recordList = recordRepository.findAll(Example.of(recordQuery));
        Map<ArcaneType, ArcaneAttainRecord> recordMap =
                recordList.stream().collect(Collectors.toMap(ArcaneAttainRecord::getArcaneType, e -> e));

        for (AttainItem attainItem : attainList) {
            ArcaneType type = attainItem.getArcaneType();
            Arcane arcane = arcaneMap.get(type);
            Integer attainCount = attainItem.getAttainCount();
            if (recordMap.containsKey(type)) {
                ArcaneAttainRecord record = recordMap.get(type);
                record.setAttainCount(attainCount);
                arcane.updateLevelInfo(record.getStartTotalCount(), attainCount);
            } else {
                arcane.updateLevelInfo(arcane.getTotalCount(), attainCount);
                ArcaneAttainRecord newRecord = ArcaneAttainRecord.builder()
                        .characterId(characterId)
                        .arcaneType(attainItem.getArcaneType())
                        .attainCount(attainCount)
                        .startTotalCount(arcane.getTotalCount())
                        .noteDate(noteDate)
                        .createTime(Timestamp.from(Instant.now()))
                        .build();
                recordList.add(newRecord);
            }
        }

        recordRepository.saveAll(recordList);
        arcaneRepository.saveAll(arcaneList);
    }

    /**
     * @param list
     */
    @Override
    public void addArcane(List<InitArcaneItem> list) {
        Optional<Character> characterOpt = characterRepository.findCurrent();
        if (!characterOpt.isPresent()) {
            throw new EntityNotExistException("未指定角色");
        }

        Character character = characterOpt.get();
        JSONObject characterInfo = characterService.queryCharacterInfo(character.getCharacterName());
        Integer level = characterInfo.getInt("Level");
        list.stream()
                .filter(e -> e.getArcaneType().getMinLevel() > level)
                .forEach(e -> {
                    e.setCurrentCount(0);
                    e.setCurrentLevel(0);
                });


        List<Arcane> arcaneList = list.stream()
                .map(e -> Arcane.builder()
                        .characterId(character.getId())
                        .arcaneType(e.getArcaneType())
                        .currentCount(e.getCurrentCount())
                        .build())
                .peek(Arcane::calculateTotalCount)
                .collect(Collectors.toList());
        arcaneRepository.saveAll(arcaneList);

    }
}
