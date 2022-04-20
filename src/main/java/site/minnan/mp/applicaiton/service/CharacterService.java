package site.minnan.mp.applicaiton.service;

import cn.hutool.json.JSONObject;
import site.minnan.mp.domain.aggregate.Character;
import site.minnan.mp.domain.entity.CharacterInfo;
import site.minnan.mp.infrastructure.enumerate.ArcaneType;
import site.minnan.mp.userinterface.dto.character.AddCharacterDTO;
import site.minnan.mp.userinterface.dto.DetailsQueryDTO;
import site.minnan.mp.userinterface.dto.character.QueryCharacterInfoDTO;

import java.util.List;

/**
 * 角色服务
 *
 * @author Minnan on 2022/03/30
 */
public interface CharacterService {

    /**
     * 查询角色列表
     */
    List<Character> getCharacterList();

    /**
     * 获取当前查看的角色
     *
     * @return
     */
    Character getCurrentCharacter();

    /**
     * 添加角色
     */
    void addCharacter(AddCharacterDTO dto);

    /**
     * 远程查询角色信息
     *
     * @param dto
     * @return
     */
    CharacterInfo getCharacterInfo(QueryCharacterInfoDTO dto);

    /**
     * 切换角色
     */
    void switchCharacter(DetailsQueryDTO dto);

    /**
     * 获取当前角色岛球列表
     */
    List<ArcaneType> getCurrentCharacterArcType();

    /**
     * 查询角色
     *
     * @param characterName
     * @return
     */
    CharacterInfo queryCharacterInfo(String characterName);
}
