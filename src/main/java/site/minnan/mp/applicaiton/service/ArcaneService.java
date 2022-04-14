package site.minnan.mp.applicaiton.service;

import site.minnan.mp.domain.aggregate.Arcane;
import site.minnan.mp.userinterface.dto.arcane.AddAttainRecordDTO;
import site.minnan.mp.userinterface.dto.arcane.InitArcaneItem;

import java.util.List;

/**
 * 岛球服务
 *
 * @author Minnan on 2022/04/13
 */
public interface ArcaneService {

    /**
     * 添加或更新岛球获取记录
     *
     * @param param
     */
    void addOrUpdateArcaneAttainRecord(AddAttainRecordDTO param);

    /**
     * @param list
     */
    void addArcane(List<InitArcaneItem> list);

    /**
     * 获取岛球信息列表
     *
     * @return
     */
    List<Arcane> getArcaneList();
}
