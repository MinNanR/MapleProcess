package site.minnan.mp.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.minnan.mp.infrastructure.enumerate.ArcaneType;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 当前岛球数量表
 *
 * @author Minnan on 2022/04/13
 */
@Entity
@Table(name = "maple_arcane")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Arcane {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * 角色id
     */
    @Column
    private Integer characterId;

    /**
     * 岛球类型
     */
    @Enumerated(value = EnumType.STRING)
    @Column
    private ArcaneType arcaneType;

    /**
     * 当前岛球等级
     */
    @Column
    private Integer level;

    /**
     * 当前岛球数量
     */
    @Column
    private Integer currentCount;

    /**
     * 当前岛球总数
     */
    @Column
    private Integer totalCount;

    /**
     * 计算当前等级所需岛球数量
     *
     * @return
     */
    public int countNeed() {
        return level * level + 11;
    }

    /**
     * 岛球升级所需数量需求表
     */
    public static final Map<Integer, Integer> needMap;

    static {
        needMap = new HashMap<>();
        for (int i = 1; i <= 20; i++) {
            needMap.put(i, i * i + 11);
        }
    }

    public int calculateStartCurrentCount(int startTotalCount) {
        int i = 1;
        while (startTotalCount > (needMap.get(i))) {
            startTotalCount -= needMap.get(i);
            i++;
        }
        return startTotalCount;
    }

    /**
     * 更新当前等级的岛球信息
     */
    public void updateLevelInfo(Integer startTotalCount, Integer attain) {
        this.totalCount = startTotalCount + attain;
        int newCurrentCount = calculateStartCurrentCount(startTotalCount) + attain;
        int countNeed = needMap.get(level);
        this.currentCount = newCurrentCount % countNeed;
        this.level = level + newCurrentCount / countNeed;
    }

    public void calculateTotalCount(){
        int totalCount = Stream.iterate(1, i -> i + 1).mapToInt(needMap::get).limit(level).sum();
        this.totalCount = totalCount + this.currentCount;
    }
}
