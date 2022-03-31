package site.minnan.mp.applicaiton.service;

import site.minnan.mp.domain.aggregate.Character;

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
}
