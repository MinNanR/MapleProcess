package site.minnan.mp.domain.aggregate;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "maple_character")
@Data
public class Character {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * 角色名称
     */
    @Column
    private String characterName;

    /**
     * 等级
     */
    @Column
    private Integer level;

    /**
     * 职业
     */
    @Column
    private String job;

    /**
     * 是否为当前查看的角色(0-否，1-是）
     */
    @Column(name = "current", columnDefinition = "int default 0")
    private Integer current;

    public static Character ofCurrent(){
        Character character = new Character();
        character.setCurrent(1);
        return character;
    }
}
