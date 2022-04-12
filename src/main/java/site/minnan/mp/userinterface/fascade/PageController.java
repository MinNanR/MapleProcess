package site.minnan.mp.userinterface.fascade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import site.minnan.mp.applicaiton.service.CharacterService;
import site.minnan.mp.domain.aggregate.Character;
import site.minnan.mp.infrastructure.enumerate.ArcaneType;

import java.util.List;

/**
 * 页面controller
 *
 * @author Minnan on 2022/04/09
 */
@Controller
@RequestMapping("/page")
public class PageController {

    @Autowired
    private CharacterService characterService;

    @GetMapping("/character")
    public ModelAndView characterList() {
        List<Character> characterList = characterService.getCharacterList();
        ModelAndView mv = new ModelAndView("character");
        mv.addObject("characterList", characterList);
        return mv;
    }

    @GetMapping("/arc")
    public ModelAndView arc(){
        ModelAndView mv = new ModelAndView();

        List<ArcaneType> currentArcList = characterService.getCurrentCharacterArcType();
        mv.addObject("currentList", currentArcList);

        mv.setViewName("arc");
        return mv;
    }
}
