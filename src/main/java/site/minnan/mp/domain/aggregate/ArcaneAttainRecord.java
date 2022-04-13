package site.minnan.mp.domain.aggregate;

/**
 * 岛球获取记录
 *
 * @author Minnan on 2022/04/13
 */

import lombok.Data;
import site.minnan.mp.infrastructure.enumerate.ArcaneType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 当前岛球数量表
 *
 * @author Minnan on 2022/04/13
 */
@Entity
@Table(name = "maple_arcane_attain_record")
@Data
public class ArcaneAttainRecord {

    /**
     * id
     */
    @Id
    @Column
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
     * 当日获取数量
     */
    @Column
    private Integer attainCount;

    /**
     * 当日开始总数量
     */
    @Column
    private Integer startTotalCount;

    /**
     * 获取的日期
     */
    @Column
    private Date noteDate;

    /**
     * 创建时间
     */
    @Column
    private Timestamp createTime;
}
