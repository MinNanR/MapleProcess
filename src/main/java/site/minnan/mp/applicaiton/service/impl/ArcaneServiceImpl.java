package site.minnan.mp.applicaiton.service.impl;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import site.minnan.mp.applicaiton.service.ArcaneService;
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

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private ArcaneAttainRecordRepository recordRepository;

    /**
     * 添加或更新岛球获取记录
     *
     * @param dto
     */
    @Override
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
        recordQuery.setCharacterId(characterId);
        recordQuery.setNoteDate(DateUtil.parse(dto.getNoteDate(), "yyyy-MM-dd"));
        List<ArcaneAttainRecord> recordList = recordRepository.findAll(Example.of(recordQuery));
        Map<ArcaneType, ArcaneAttainRecord> recordMap =
                recordList.stream().collect(Collectors.toMap(e -> e.getArcaneType(), e -> e));

        for (AttainItem attainItem : attainList) {
            ArcaneType type = attainItem.getArcaneType();
            if (recordMap.containsKey(type)) {
                ArcaneAttainRecord record = recordMap.get(type);
                Integer attainCount = attainItem.getAttainCount();
                record.setAttainCount(attainCount);
                Arcane arcane = arcaneMap.get(type);
            }


        }
    }
}
