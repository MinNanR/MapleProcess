package site.minnan.mp.infrastructure.config;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.RuntimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.minnan.mp.domain.entity.Menu;
import site.minnan.mp.infrastructure.interceptor.CharacterInterceptor;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 页面配置
 *
 * @author Minnan on 2022/04/02
 */
@Configuration
public class PageConfig implements WebMvcConfigurer {


    @Autowired
    private CharacterInterceptor characterInterceptor;

    /**
     * Configure simple automated controllers pre-configured with the response
     * status code and/or a view to render the response body. This is useful in
     * cases where there is no need for custom controller logic -- e.g. render a
     * home page, perform simple site URL redirects, return a 404 status with
     * HTML content, a 204 with no content, and more.
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/page/index").setViewName("index");
        registry.addViewController("/page/addCharacter").setViewName("addCharacter");
        registry.addViewController("/page/exp").setViewName("exp");
    }

    /**
     * Add Spring MVC lifecycle interceptors for pre- and post-processing of
     * controller method invocations and resource handler requests.
     * Interceptors can be registered to apply to all requests or be limited
     * to a subset of URL patterns.
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(characterInterceptor);
    }

    private static final List<Menu> menuList;

    static {
        menuList = ListUtil.toList(
                Menu.of("/page/index", "主页", "index"),
                Menu.of("/page/exp", "经验", "exp"),
                Menu.of("/page/arc", "岛球", "arc")
        );

    }

    public static List<Menu> getMenu() {
        return menuList;
    }
}
