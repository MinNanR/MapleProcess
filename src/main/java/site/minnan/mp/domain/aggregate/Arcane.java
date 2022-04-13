package site.minnan.mp.domain.aggregate;

import lombok.Data;
import org.checkerframework.checker.units.qual.C;
import site.minnan.mp.infrastructure.enumerate.ArcaneType;

import javax.persistence.*;

/**
 * 当前岛球数量表
 *
 * @author Minnan on 2022/04/13
 */
@Entity
@Table(name = "maple_arcane")
@Data
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

    public int calculateStartCurrentCount(int startTotalCount) {
        int i = 1;
        while (startTotalCount > (i * i + 11)) {
            startTotalCount -= i * i + 11;
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
        int countNeed = level * level + 11;
        this.currentCount = newCurrentCount % countNeed;
        this.level = level + newCurrentCount / countNeed;

    }
}
