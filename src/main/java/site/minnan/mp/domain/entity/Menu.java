package site.minnan.mp.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 菜单对象
 *
 * @author Minnan on 2022/04/02
 */
@Data
@AllArgsConstructor
public class Menu {

    /**
     * 地址
     */
    private String url;

    /**
     * 菜单名称
     */
    private String label;

    /**
     * 视图名称
     */
    private String viewName;

    public static Menu of(String url, String label, String viewName) {
        return new Menu(url, label, viewName);
    }
}
