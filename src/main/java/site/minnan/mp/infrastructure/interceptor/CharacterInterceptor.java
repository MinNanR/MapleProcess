package site.minnan.mp.infrastructure.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import site.minnan.mp.applicaiton.service.CharacterService;
import site.minnan.mp.domain.aggregate.Character;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 向页面注入公共参数
 *
 * @author Minnan on 2022/04/15
 */
@Component
public class CharacterInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CharacterService characterService;

    /**
     * This implementation always returns {@code true}.
     *
     * @param request
     * @param response
     * @param handler
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        Character character = characterService.getCurrentCharacter();
        session.setAttribute("currentCharacter", character);
        return super.preHandle(request, response, handler);
    }
}
