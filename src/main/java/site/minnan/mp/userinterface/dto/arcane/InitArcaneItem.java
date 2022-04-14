package site.minnan.mp.userinterface.dto.arcane;

import lombok.Data;
import site.minnan.mp.infrastructure.enumerate.ArcaneType;

/**
 * 初始
 *
 * @author Minnan on 2022/04/13
 */
@Data
public
class InitArcaneItem {

    /**
     * 岛球类型
     */
    private ArcaneType arcaneType;

    /**
     * 当前等级
     */
    private Integer currentLevel;

    /**
     * 当前数量
     */
    private Integer currentCount;

    public void setArcaneType(String typeName) {
        this.arcaneType = ArcaneType.valueOf(typeName);
    }
}
