package site.minnan.mp.userinterface.fascade;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.mp.applicaiton.service.ArcaneService;
import site.minnan.mp.userinterface.dto.arcane.AddAttainRecordDTO;
import site.minnan.mp.userinterface.dto.arcane.AttainItem;
import site.minnan.mp.userinterface.response.ResponseEntity;

import java.util.List;

/**
 * 岛球页面控制器
 *
 * @author Minnan on 2022/03/30
 */
@RestController
@RequestMapping("/api/arc")
@Slf4j
public class ArcaneController {

    @Autowired
    private ArcaneService arcaneService;

    /**
     * 更新岛球获取记录
     * @param param
     * @return
     */
    @PostMapping("addArcaneAttainRecord")
    public ResponseEntity<?> addArcaneAttainRecord(@RequestBody AddAttainRecordDTO param) {
        arcaneService.addOrUpdateArcaneAttainRecord(param);
        return ResponseEntity.success("操作成功");
    }

    /**
     * 初始化岛球信息
     * @param dto
     * @return
     */
    @PostMapping("initArcane")
    public ResponseEntity<?> initArcane(@RequestBody List<AttainItem> dto){

        return ResponseEntity.success();
    }

}
