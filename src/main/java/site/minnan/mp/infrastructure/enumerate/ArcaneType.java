package site.minnan.mp.infrastructure.enumerate;

/**
 * 岛球地图类型
 *
 * @author Minnan on 2022/03/30
 */
public enum ArcaneType {

    VANISHING_JOURNEY("消亡旅途", 1),
    CHU_CHU_ISLAND("啾啾岛", 2),
    LACHELEIN("拉克兰", 3),
    ARCANA("阿尔卡那", 4),
    MORASS("莫拉斯", 5),
    ESFERNA("埃斯佩拉", 6);

    ArcaneType(String chineseName, Integer ordinal) {
        this.chineseName = chineseName;
        this.ordinal = ordinal;
    }

    final String chineseName;

    final Integer ordinal;

}
