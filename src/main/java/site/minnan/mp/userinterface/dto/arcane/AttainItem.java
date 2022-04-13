package site.minnan.mp.userinterface.dto.arcane;

import lombok.Data;
import site.minnan.mp.infrastructure.enumerate.ArcaneType;

/**
 * 添加岛球获取记录参数
 *
 * @author Minnan on 2022/04/13
 */
@Data
public class AttainItem {

    /**
     * 岛球类型
     */
    private ArcaneType arcaneType;

    /**
     * 获取个数
     */
    private Integer attainCount;

    public void setArcaneType(String typeName) {
        this.arcaneType = ArcaneType.valueOf(typeName);
    }

    /**
     * 判断是否填写了该岛数字
     */
    public boolean isFill() {
        return attainCount == null;
    }
}
