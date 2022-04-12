package site.minnan.mp.infrastructure.config;

import cn.hutool.core.collection.ListUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.minnan.mp.domain.entity.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面配置
 *
 * @author Minnan on 2022/04/02
 */
@Configuration
public class PageConfig implements WebMvcConfigurer {


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
    }

    private static final List<Menu> menuList;

    static {
        menuList = ListUtil.toList(
                Menu.of("/page/index", "主页", "index"),
                Menu.of("/page/arc", "岛球", "arc")
        );

    }

    public static List<Menu> getMenu() {
        return menuList;
    }
}
