package site.minnan.mp.infrastructure.utils;

import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 经验工具类
 *
 * @author Minnan on 2022/04/24
 */
@Component
@Slf4j
public class ExpUtils {

    private static final Map<Integer, BigDecimal> expMap;

    private static final List<Integer> stageLevelList;

    static {
        expMap = new HashMap<>();
        stageLevelList = new ArrayList<>();
        CsvReader reader = CsvUtil.getReader();
        ClassPathResource expResource = new ClassPathResource("exp.csv");
        try {
            CsvData expData = reader.read(expResource.getFile(), CharsetUtil.CHARSET_UTF_8);
            for (CsvRow row : expData) {
                int lv = Integer.parseInt(row.get(0));
                BigDecimal exp = new BigDecimal(row.get(1));
                expMap.put(lv, exp);
                stageLevelList.add(lv);
            }
        } catch (IOException e) {
            log.error("读取经验文件异常");
        }
    }


    /**
     * 获取到下一个阶段的经验值总量
     *
     * @param nextStage 下一阶段等级
     */
    public BigDecimal getNextStageExp(int nextStage) {
        return expMap.get(nextStage);
    }

    /**
     * 获取下一阶段等级
     *
     * @param lv 当前等级
     * @return
     */
    public Integer getNextStage(int lv) {
        if (lv < 210) {
            return 210;
        }
        int size = stageLevelList.size();
        int i = 0;
        for (; i < size - 1; i++) {
            if (lv >= stageLevelList.get(i) && lv < stageLevelList.get(i + 1)) {
                return stageLevelList.get(i + 1);
            }
        }
        return stageLevelList.get(i + 1);
    }

}
