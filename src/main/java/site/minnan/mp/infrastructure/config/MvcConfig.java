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
public class MvcConfig implements WebMvcConfigurer {


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
        menuList.forEach(e -> registry.addViewController(e.getUrl()).setViewName(e.getViewName()));
    }

    private static final List<Menu> menuList;

    static {
        menuList = ListUtil.toList(
                Menu.of("/index", "主页", "index"),
                Menu.of("/arc", "岛球", "arc")
        );

    }

    public static List<Menu> getMenu() {
        return menuList;
    }
}
