package site.minnan.mp.userinterface.fascade;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import site.minnan.mp.applicaiton.service.CharacterService;
import site.minnan.mp.domain.aggregate.Character;
import site.minnan.mp.domain.entity.CharacterInfo;
import site.minnan.mp.userinterface.dto.AddCharacterDTO;
import site.minnan.mp.userinterface.dto.DetailsQueryDTO;
import site.minnan.mp.userinterface.dto.QueryCharacterInfoDTO;
import site.minnan.mp.userinterface.response.ResponseEntity;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色控制器
 *
 * @author Minnan on 2022/04/04
 */
@RestController
@Slf4j
public class CharacterController {

    @Autowired
    private CharacterService characterService;


    @PostMapping("/api/getCharacterInfo")
    public ResponseEntity<CharacterInfo> getCharacterInfo(@RequestBody @Valid QueryCharacterInfoDTO dto) {
        log.info("查询角色：{}", dto.getCharacterName());
        CharacterInfo vo = characterService.getCharacterInfo(dto);
        return ResponseEntity.success(vo);
    }

    @PostMapping("/api/addCharacter")
    public ResponseEntity<?> addCharacter(@RequestBody AddCharacterDTO dto) {
        characterService.addCharacter(dto);
        return ResponseEntity.success();
    }

    @PostMapping("/api/switchCharacter")
    public ResponseEntity<?> switchCharacter(@RequestBody DetailsQueryDTO dto) {
        characterService.switchCharacter(dto);
        return ResponseEntity.success();
    }
}
