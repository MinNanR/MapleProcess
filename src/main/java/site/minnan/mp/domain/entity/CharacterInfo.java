package site.minnan.mp.domain.entity;

import cn.hutool.json.JSONObject;
import lombok.Getter;

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
     * 角色图片地址
     */
    private String characterImageUrl;

    public CharacterInfo(JSONObject json) {
        this.characterName = json.getStr("Name");
        this.job = json.getStr("Class");
        this.level = json.getInt("Level");
        this.characterImageUrl = json.getStr("CharacterImageURL");
    }
}
