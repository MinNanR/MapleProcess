package site.minnan.mp.userinterface.fascade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import site.minnan.mp.applicaiton.service.ArcaneService;
import site.minnan.mp.applicaiton.service.CharacterService;
import site.minnan.mp.domain.aggregate.Arcane;
import site.minnan.mp.domain.aggregate.Character;
import site.minnan.mp.domain.entity.CharacterInfo;
import site.minnan.mp.domain.vo.ArcaneListVO;
import site.minnan.mp.domain.vo.AttainChartDataVO;
import site.minnan.mp.infrastructure.enumerate.ArcaneType;
import site.minnan.mp.infrastructure.exception.EntityNotExistException;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 页面controller
 *
 * @author Minnan on 2022/04/09
 */
@Controller
@RequestMapping("/page")
@Slf4j
public class PageController {

    @Autowired
    private CharacterService characterService;

    @Autowired
    private ArcaneService arcaneService;

    @GetMapping("/character")
    public ModelAndView characterList() {
        List<Character> characterList = characterService.getCharacterList();
        ModelAndView mv = new ModelAndView("character");
        mv.addObject("characterList", characterList);
        return mv;
    }

    @GetMapping("/arc")
    public ModelAndView arc() {
        ModelAndView mv = new ModelAndView();

        try {
            List<ArcaneType> currentArcList = characterService.getCurrentCharacterArcType();
            List<ArcaneListVO> arcaneList = arcaneService.getArcaneList();
            mv.addObject("arcaneList", arcaneList);
            mv.addObject("currentList", currentArcList);
            mv.addObject("allArcaneType", ArcaneType.values());

            List<AttainChartDataVO> chartDataList =
                    arcaneList.stream().map(ArcaneListVO::getAttainChartData).collect(Collectors.toList());
            mv.addObject("chartDataList", chartDataList);
            mv.setViewName("arc");
        } catch (EntityNotExistException e) {
            log.warn("未指定角色");
            RedirectView redirectView = new RedirectView("character");
            mv.setView(redirectView);
        }


        return mv;
    }

    @RequestMapping("/exp")
    public ModelAndView exp(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        try {
            Character character = (Character) session.getAttribute("currentCharacter");
            CharacterInfo characterInfo = characterService.queryCharacterInfo(character.getCharacterName());

            mv.setViewName("exp");
            mv.addObject("characterInfo", characterInfo);
            mv.addObject("graphDataList", characterInfo.getGraphDataList());

        } catch (EntityNotExistException e) {
            RedirectView redirectView = new RedirectView("character");
            mv.setView(redirectView);
        }

        return mv;
    }
}
