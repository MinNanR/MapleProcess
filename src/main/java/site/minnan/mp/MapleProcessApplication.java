package site.minnan.mp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 主程序
 *
 * @author Minnan on 2022/03/30
 */
@SpringBootApplication
@EnableCaching
public class MapleProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapleProcessApplication.class, args);
    }

}
