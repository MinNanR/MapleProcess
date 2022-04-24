package site.minnan.mp.domain.entity;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 角色经验图标数据
 *
 * @author on 2022/04/20
 */
@Data
public class GraphData {

    /**
     * 当前等级经验值
     */
    private BigDecimal currentExp;

    /**
     * 当日经验增长
     */
    private BigDecimal expDifference;

    /**
     * 到下一级所需的经验
     */
    private BigDecimal expToNextLevel;

    /**
     * 经验总量
     */
    private BigDecimal totalOverallExp;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 日期
     */
    private String date;

    public GraphData(JSONObject json) {
        currentExp = json.getBigDecimal("CurrentEXP");
        expDifference = json.getBigDecimal("EXPDifference");
        expToNextLevel = json.getBigDecimal("EXPToNextLevel");
        level = json.getInt("Level");
        totalOverallExp = json.getBigDecimal("TotalOverallEXP");
        date = json.getStr("DateLabel");
    }

    public BigDecimal getPercent() {
        return currentExp.multiply(expToNextLevel);
    }

}
