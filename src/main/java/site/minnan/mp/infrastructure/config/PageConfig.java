package site.minnan.mp.infrastructure.config;

import cn.hutool.core.util.RuntimeUtil;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class PageConfig {

//    @PostConstruct
    public void openBrowser(){
        RuntimeUtil.exec("cmd /c start http://localhost:9000/index.html");
    }
}
