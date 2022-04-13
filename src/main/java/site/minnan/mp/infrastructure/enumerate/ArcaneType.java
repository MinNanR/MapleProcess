package site.minnan.mp.infrastructure.enumerate;

import cn.hutool.core.util.StrUtil;

/**
 * 岛球地图类型
 *
 * @author Minnan on 2022/03/30
 */
public enum ArcaneType {

    VANISHING_JOURNEY("消亡旅途", 1, 200),
    CHU_CHU_ISLAND("啾啾岛", 2, 210),
    LACHELEIN("拉克兰", 3, 220),
    ARCANA("阿尔卡那", 4,225),
    MORASS("莫拉斯", 5, 230),
    ESFERNA("埃斯佩拉", 6, 235);

    ArcaneType(String chineseName, Integer ordinal, Integer minLevel) {
        this.chineseName = chineseName;
        this.ordinal = ordinal;
        this.minLevel = minLevel;
    }

    /**
     * 中文名
     */
    final String chineseName;

    /**
     * 排序
     */
    final Integer ordinal;

    /**
     * 最低等级
     */
    final Integer minLevel;

    public String getChineseName() {
        return chineseName;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public Integer getMinLevel() {
        return minLevel;
    }

    public String label(){
        return StrUtil.format("{}({}岛)", chineseName, ordinal);
    }
}
