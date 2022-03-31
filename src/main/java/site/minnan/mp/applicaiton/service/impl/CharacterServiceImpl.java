package site.minnan.mp.applicaiton.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.mp.applicaiton.service.CharacterService;
import site.minnan.mp.domain.aggregate.Character;
import site.minnan.mp.domain.repository.CharacterRepository;

import java.util.List;

/**
 * 角色service
 *
 * @author Minnan on 2022/03/30
 */
@Service
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    private CharacterRepository characterRepository;


    /**
     * 查询角色列表
     */
    @Override
    public List<Character> getCharacterList() {
        return characterRepository.findAll();
    }
}
