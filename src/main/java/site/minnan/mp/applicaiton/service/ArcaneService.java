package site.minnan.mp.applicaiton.service;

import cn.hutool.json.JSONObject;
import site.minnan.mp.userinterface.dto.arcane.AddAttainRecordDTO;

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
}
