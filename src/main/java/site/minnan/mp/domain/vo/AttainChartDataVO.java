package site.minnan.mp.domain.vo;

import cn.hutool.core.date.DateUtil;
import lombok.Data;
import site.minnan.mp.infrastructure.enumerate.ArcaneType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 获取记录表格数据
 *
 * @author Minnan on 2022/04/17
 */
@Data
public class AttainChartDataVO {

    private ArcaneType arcaneType;

    /**
     * 日期列表
     */
    List<String> dateList;

    /**
     * 获取数量列表
     */
    List<Integer> attainCountList;

    public void add(String date ,Integer attainCount){
        this.dateList.add(date);
        this.attainCountList.add(attainCount);
    }

    public AttainChartDataVO(ArcaneType arcaneType){
        this.arcaneType = arcaneType;
        this.dateList = new ArrayList<>();
        this.attainCountList = new ArrayList<>();
    }

}
