package site.minnan.mp.userinterface.fascade;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import site.minnan.mp.applicaiton.service.CharacterService;
import site.minnan.mp.domain.aggregate.Character;
import site.minnan.mp.domain.entity.CharacterInfo;
import site.minnan.mp.userinterface.dto.AddCharacterDTO;
import site.minnan.mp.userinterface.dto.QueryCharacterInfoDTO;
import site.minnan.mp.userinterface.response.ResponseEntity;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色控制器
 *
 * @author Minnan on 2022/04/04
 */
@Controller
@Slf4j
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @GetMapping("/page/character")
    public ModelAndView characterList() {
        List<Character> characterList = characterService.getCharacterList();
        ModelAndView mv = new ModelAndView("character");
        mv.addObject("characterList", characterList);
        return mv;
    }

    @PostMapping("/api/getCharacterInfo")
    @ResponseBody
    public ResponseEntity<CharacterInfo> getCharacterInfo(@RequestBody @Valid QueryCharacterInfoDTO dto){
        CharacterInfo vo = characterService.getCharacterInfo(dto);
        return ResponseEntity.success(vo);
    }

    @PostMapping("/api/addCharacter")
    @ResponseBody
    public ResponseEntity<?> addCharacter(@RequestBody AddCharacterDTO dto){
        log.info("request: {}", JSONUtil.toJsonStr(dto));
        characterService.addCharacter(dto);
        return ResponseEntity.success();
    }
}
