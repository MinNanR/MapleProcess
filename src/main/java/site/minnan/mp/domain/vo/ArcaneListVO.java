package site.minnan.mp.domain.vo;

import lombok.Data;
import site.minnan.mp.domain.aggregate.Arcane;
import site.minnan.mp.infrastructure.enumerate.ArcaneType;

/**
 * 岛球列表显示参数
 *
 * @author Minnan on 2022/04/15
 */
@Data
public class ArcaneListVO {

    private Integer id;

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

    /**
     * 过去三天是不是鸽了
     */
    private boolean isDove3;

    /**
     * 过去七天是不是鸽了
     */
    private boolean isDove7;

    /**
     * 获取记录表格
     */
    private AttainChartDataVO attainChartData;

    public ArcaneListVO(Arcane arcane){
        this.id = arcane.getId();
        this.arcaneType = arcane.getArcaneType();
        this.currentLevel = arcane.getLevel();
        this.currentCount = arcane.getCurrentCount();
        this.totalCount = arcane.getTotalCount();
        this.countToMaxLevel = Arcane.needMap.values().stream().mapToInt(e -> e).sum();
        this.countToNextLevel = Arcane.needMap.get(this.currentLevel);
        this.isDove3 = true;
        this.isDove7 = true;
        this.attainChartData = new AttainChartDataVO(arcaneType);
    }
}
