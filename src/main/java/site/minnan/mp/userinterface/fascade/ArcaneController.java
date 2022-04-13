package site.minnan.mp.userinterface.fascade;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.mp.userinterface.dto.arcane.AddAttainRecordDTO;
import site.minnan.mp.userinterface.response.ResponseEntity;

/**
 * 岛球页面控制器
 *
 * @author Minnan on 2022/03/30
 */
@RestController
@RequestMapping("/api/arc")
@Slf4j
public class ArcaneController {

    @PostMapping("addArcaneAttainRecord")
    public ResponseEntity<?> addArcaneAttainRecord(@RequestBody AddAttainRecordDTO param) {
        return ResponseEntity.success("操作成功");
    }


}
