package site.minnan.mp.domain.entity;

import cn.hutool.json.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息，远程查询
 *
 * @author Minnan on 2022/04/05
 */
@Getter
public class CharacterInfo {

    /**
     * 角色名称
     */
    private String characterName;

    /**
     * 职业
     */
    private String job;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 职业排名
     */
    private Integer jobRank;

    /**
     * 角色图片地址
     */
    private String characterImageUrl;

    /**
     * 成就点
     */
    private Integer achievementPoints;

    /**
     * 成就排名
     */
    private Integer achievementRank;

    /**
     * 当前等级经验数量
     */
    private BigDecimal exp;

    /**
     * 经验百分比
     */
    private BigDecimal expPercent;


    /**
     * 等级总排名
     */
    private Integer globalRanking;

    /**
     * 家族
     */
    private String guild;

    /**
     * 每日联盟币
     */
    private Integer legionCoinsPerDay;

    /**
     * 联盟等级
     */
    private Integer legionLevel;

    /**
     * 联盟战斗力
     */
    private Integer legionPower;

    /**
     * 联盟排名
     */
    private Integer legionRank;

    /**
     * 服务器
     */
    private String server;

    /**
     * 服务器职业排名
     */
    private Integer serverClassRanking;

    /**
     * 服务器等级总排名
     */
    private Integer serverRank;

    /**
     * 图标数据
     */
    private List<GraphData> graphDataList;

    /**
     * 下一阶段等级
     */
    @Setter
    private Integer nextStageLevel;

    /**
     * 根据最近1天肝度到达下一阶段所需天数
     */
    @Setter
    private BigDecimal dayToNextStageOne;

    /**
     * 根据最近14天肝度到达下一阶段所需天数
     */
    @Setter
    private BigDecimal dayToNextStageFourteen;

    public CharacterInfo(JSONObject json) {
        this.characterName = json.getStr("Name");
        this.job = json.getStr("Class");
        this.level = json.getInt("Level");
        this.characterImageUrl = json.getStr("CharacterImageURL");
        this.achievementPoints = json.getInt("AchievementPoints");
        this.achievementRank = json.getInt("AchievementRank");
        this.jobRank = json.getInt("ClassRank");
        this.exp = json.getBigDecimal("EXP");
        this.expPercent = BigDecimal.valueOf(json.getDouble("EXPPercent"));
        this.globalRanking = json.getInt("GlobalRanking");
        this.guild = json.getStr("Guild");
        this.legionCoinsPerDay = json.getInt("LegionCoinsPerDay");
        this.legionLevel = json.getInt("LegionLevel");
        this.legionPower = json.getInt("LegionPower");
        this.legionRank = json.getInt("LegionRank");
        this.server = json.getStr("Server");
        this.serverClassRanking = json.getInt("ServerClassRanking");
        this.serverRank = json.getInt("ServerRank");

        graphDataList = json.getJSONArray("GraphData")
                .stream()
                .map(e -> (JSONObject) e)
                .map(GraphData::new)
                .collect(Collectors.toList());
    }

    public List<String> dateList() {
        return this.graphDataList.stream()
                .map(GraphData::getDate)
                .collect(Collectors.toList());
    }

    public List<BigDecimal> expPerDay() {
        BigDecimal oneBillion = new BigDecimal("1000000000");
        return this.graphDataList.stream()
                .map(GraphData::getExpDifference)
                .map(e -> e.divide(oneBillion))
                .collect(Collectors.toList());
    }

    public List<BigDecimal> overAllExp() {
        BigDecimal oneBillion = new BigDecimal("1000000000");
        return this.graphDataList.stream()
                .map(GraphData::getTotalOverallExp)
                .map(e -> e.divide(oneBillion))
                .collect(Collectors.toList());
    }

    public List<Integer> levelLine() {
        return this.graphDataList.stream().map(GraphData::getLevel).collect(Collectors.toList());
    }

}
