package site.minnan.mp.applicaiton.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.minnan.mp.applicaiton.service.ArcaneService;
import site.minnan.mp.applicaiton.service.CharacterService;
import site.minnan.mp.domain.aggregate.Arcane;
import site.minnan.mp.domain.aggregate.ArcaneAttainRecord;
import site.minnan.mp.domain.aggregate.Character;
import site.minnan.mp.domain.entity.CharacterInfo;
import site.minnan.mp.domain.repository.ArcaneAttainRecordRepository;
import site.minnan.mp.domain.repository.ArcaneRepository;
import site.minnan.mp.domain.vo.ArcaneListVO;
import site.minnan.mp.domain.vo.AttainChartDataVO;
import site.minnan.mp.infrastructure.annotations.CharacterRequired;
import site.minnan.mp.infrastructure.enumerate.ArcaneType;
import site.minnan.mp.userinterface.dto.arcane.AddAttainRecordDTO;
import site.minnan.mp.userinterface.dto.arcane.AttainItem;
import site.minnan.mp.userinterface.dto.arcane.InitArcaneItem;

import javax.persistence.criteria.Path;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 岛球服务
 *
 * @author Minnan on 2022/04/13
 */
@Service
@Slf4j
public class ArcaneServiceImpl implements ArcaneService {

    @Autowired
    private ArcaneRepository arcaneRepository;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private ArcaneAttainRecordRepository recordRepository;

    @Autowired
    private HttpSession session;

    /**
     * 添加或更新岛球获取记录
     *
     * @param dto
     */
    @Override
    @Transactional
    @CharacterRequired
    public void addOrUpdateArcaneAttainRecord(AddAttainRecordDTO dto) {
        List<AttainItem> attainList = dto.getAttainList();

        Character character = (Character) session.getAttribute("currentCharacter");
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
                ArcaneAttainRecord newRecord = ArcaneAttainRecord.builder()
                        .characterId(characterId)
                        .arcaneType(attainItem.getArcaneType())
                        .attainCount(attainCount)
                        .startTotalCount(arcane.getTotalCount())
                        .noteDate(noteDate)
                        .createTime(Timestamp.from(Instant.now()))
                        .build();
                arcane.updateLevelInfo(arcane.getTotalCount(), attainCount);
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
    @CharacterRequired
    public void addArcane(List<InitArcaneItem> list) {
        Character character = (Character) session.getAttribute("currentCharacter");
        CharacterInfo characterInfo = characterService.queryCharacterInfo(character.getCharacterName());
        Integer level = characterInfo.getLevel();

        list.stream()
                .filter(e -> (e.getArcaneType().getMinLevel() > level) ||
                        e.getCurrentCount() == null || e.getCurrentLevel() == null)
                .forEach(e -> {
                    e.setCurrentCount(0);
                    e.setCurrentLevel(0);
                });


        List<Arcane> arcaneList = list.stream()
                .map(e -> Arcane.builder()
                        .characterId(character.getId())
                        .arcaneType(e.getArcaneType())
                        .level(e.getCurrentLevel())
                        .currentCount(e.getCurrentCount())
                        .build())
                .peek(Arcane::calculateTotalCount)
                .collect(Collectors.toList());
        arcaneRepository.saveAll(arcaneList);

    }

    /**
     * 获取岛球信息列表
     *
     * @return
     */
    @Override
    @CharacterRequired
    public List<ArcaneListVO> getArcaneList() {
        Character character = (Character) session.getAttribute("currentCharacter");

        List<Arcane> arcaneList = arcaneRepository.findAllByCharacterIdIs(character.getId());

        DateTime sevenDaysAgo = DateUtil.offsetDay(DateUtil.beginOfDay(new Date()), -6);
        Specification<ArcaneAttainRecord> specification = ((root, query, criteriaBuilder) -> {
            criteriaBuilder.equal(root.get("characterId"), character.getId());
            Path<Object> noteDate = root.get("noteDate");
            return criteriaBuilder.greaterThanOrEqualTo(noteDate.as(Date.class), sevenDaysAgo);
        });

        List<ArcaneAttainRecord> recordList = recordRepository.findAll(specification);


        Map<ArcaneType, List<ArcaneAttainRecord>> groupByType = recordList.stream().collect(Collectors.groupingBy(e -> e.getArcaneType()));

        Map<ArcaneType, ArcaneListVO> resultMap = arcaneList.stream()
                .filter(e -> character.getLevel() >= e.getArcaneType().getMinLevel())
                .map(ArcaneListVO::new)
                .collect(Collectors.toMap(ArcaneListVO::getArcaneType, e -> e));


        DateTime threeDaysAgo = DateUtil.offsetDay(DateUtil.beginOfDay(new Date()), -3);

        for (ArcaneType type : resultMap.keySet()) {
            List<ArcaneAttainRecord> records = groupByType.get(type);
            ArcaneListVO vo = resultMap.get(type);

            Iterator<String> dateItr = Stream.iterate(sevenDaysAgo, d -> d.offsetNew(DateField.DAY_OF_YEAR, 1))
                    .map(e -> DateUtil.format(e, "yyyy-MM-dd"))
                    .limit(7L).iterator();
            AttainChartDataVO chartData = vo.getAttainChartData();
            if (records == null) {
                dateItr.forEachRemaining(e -> chartData.add(e, 0));
                continue;
            }

            int attainLast7 = records.stream().mapToInt(ArcaneAttainRecord::getAttainCount).sum();
            int attainLast3 =
                    records.stream().filter(e -> threeDaysAgo.isBeforeOrEquals(e.getNoteDate())).mapToInt(ArcaneAttainRecord::getAttainCount).sum();


            double attainAvgLast7 = (double) attainLast7 / 7;
            double attainAvgLast3 = (double) attainLast3 / 3;


            int countNeedToNextLevel = vo.getCountToNextLevel() - vo.getCurrentCount();
            int countNeedToMaxLevel = vo.getCountToMaxLevel() - vo.getTotalCount();

            vo.setDayCountToNextLevelSevenDay(countNeedToNextLevel / attainAvgLast7);
            vo.setDayCountToMaxLevelSevenDay(countNeedToMaxLevel / attainAvgLast7);
            vo.setDayCountToNextLevelThreeDay(countNeedToNextLevel / attainAvgLast3);
            vo.setDayCountToMaxLevelThreeDay(countNeedToMaxLevel / attainAvgLast3);

            vo.setDove7(attainLast7 == 0);
            vo.setDove3(attainLast3 == 0);


            Map<String, Integer> dateMap = records.stream().collect(Collectors.toMap(e -> DateUtil.format(e.getNoteDate(), "yyyy-MM-dd")
                    , ArcaneAttainRecord::getAttainCount));

            dateItr.forEachRemaining(d -> chartData.add(d, dateMap.getOrDefault(d, 0)));
        }

        return resultMap.values().stream()
                .sorted(Comparator.comparingInt(e -> e.getArcaneType().getOrdinal()))
                .collect(Collectors.toList());
    }

}
