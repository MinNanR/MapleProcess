package site.minnan.mp;

import cn.hutool.core.lang.Console;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import site.minnan.mp.domain.aggregate.Arcane;
import site.minnan.mp.domain.aggregate.ArcaneAttainRecord;
import site.minnan.mp.domain.entity.CharacterInfo;
import site.minnan.mp.domain.repository.ArcaneAttainRecordRepository;
import site.minnan.mp.domain.repository.ArcaneRepository;
import site.minnan.mp.domain.repository.CharacterRepository;
import site.minnan.mp.infrastructure.enumerate.ArcaneType;
import site.minnan.mp.infrastructure.utils.CharacterUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest(classes = MapleProcessApplication.class)
public class CharacterTest {

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    ArcaneAttainRecordRepository attainRecordRepository;

    @Autowired
    ArcaneRepository arcaneRepository;

    @Autowired
    CharacterUtils characterUtils;

    @Test
    public void testAddCharacter() {
        CharacterInfo characterInfo = characterUtils.queryCharacterInfo("minnanil");
        System.out.println(characterInfo);
    }

    @Test
    public void testRecord() {
//        ArcaneAttainRecord probe = new ArcaneAttainRecord();
//        probe.setNoteDate(DateUtil.parse("2022-05-01", "yyyy-MM-dd"));
//        List<ArcaneAttainRecord> recordList = attainRecordRepository.findAll(Example.of(probe));
        List<ArcaneAttainRecord> recordList = attainRecordRepository.findAll();

        Arcane probe = new Arcane();
        probe.setCharacterId(1);
        List<Arcane> arcaneList = arcaneRepository.findAll(Example.of(probe));
        Map<ArcaneType, Arcane> typeMap = arcaneList.stream().collect(Collectors.toMap(e -> e.getArcaneType(), e -> e));

        Map<Integer, Integer> needMap = Arcane.needMap;
        Map<ArcaneType, List<ArcaneAttainRecord>> groupByType =
                recordList.stream().collect(Collectors.groupingBy(ArcaneAttainRecord::getArcaneType));
        List<ArcaneType> keySet =
                groupByType.keySet().stream().sorted(Comparator.comparing(ArcaneType::getOrdinal)).collect(Collectors.toList());
        for (ArcaneType arcaneType : keySet) {
            List<ArcaneAttainRecord> list = groupByType.get(arcaneType);
            list.sort(Comparator.comparing(ArcaneAttainRecord::getNoteDate));
            ArcaneAttainRecord first = list.get(0);
            Integer startTotalCount = first.getStartTotalCount();
            int level = 0;
            while (true) {
                level++;
                Integer need = needMap.get(level);
                if (startTotalCount >= need) {
                    startTotalCount -= need;
                } else {
                    break;
                }
            }

            Console.log("{} 【 startLevel - {}, startCount - {} 】", arcaneType.label(), level, startTotalCount);

            int currentCount = startTotalCount;
            int totalCount = first.getStartTotalCount();
            for (ArcaneAttainRecord record : list) {
                Integer attainCount = record.getAttainCount();
                record.setStartLevel(level);
                record.setStartCurrentCount(currentCount);
                record.setStartTotalCount(totalCount);
                Console.log("{} 【 {} : startLevel - {}, startCount - {} , attainCount - {}】", arcaneType.label(),
                        record.getNoteDate(), level, currentCount, attainCount);
                int newCurrentCount = currentCount + attainCount;
                Integer need = needMap.get(level);
                level += newCurrentCount / need;
                currentCount = newCurrentCount % need;
                totalCount += attainCount;
            }

            Arcane arcane = typeMap.get(arcaneType);
            arcane.setLevel(level);
            arcane.setCurrentCount(currentCount);
            arcane.setTotalCount(totalCount);

            Console.log("---");

        }

        arcaneRepository.saveAll(arcaneList);
        attainRecordRepository.saveAll(recordList);

//        for (ArcaneAttainRecord record : recordList) {
//            int level = 0;
//            Integer startTotalCount = record.getStartTotalCount();
//            do {
//                level++;
//                Integer need = needMap.get(level);
//                startTotalCount -= need;
//            } while (startTotalCount >= 0);
//            System.out.println(record.getArcaneType() + ":" + level);
//        }
    }
}
