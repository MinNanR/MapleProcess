package site.minnan.mp.domain.vo;

import lombok.Data;

/**
 * 岛球列表显示参数
 *
 * @author Minnan on 2022/04/15
 */
@Data
public class ArcaneListVO {

    private Integer id;

    /**
     * 当前等级
     */
    private Integer currentLevel;

    /**
     * 当前数量
     */
    private Integer currentCount;

    /**
     * 升级所需个数
     */
    private Integer countToNextLevel;

    /**
     * 当前总数量
     */
    private Integer totalCount;

    /**
     * 满级所需个数
     */
    private Integer countToMaxLevel;

    /**
     * 按照过去3天肝度，到下一级所需天数
     */
    private Double dayCountToNextLevelThreeDay;

    /**
     * 按照过去7天肝度，到下一级所需天数
     */
    private Double dayCountToNextLevelSevenDay;

    /**
     * 按照过去3天肝度，到达满级天数
     */
    private Double dayCountToMaxLevelThreeDay;

    /**
     * 按照过去3天肝度，到达满级天数
     */
    private Double dayCountToMaxLevelSevenDay;

}
