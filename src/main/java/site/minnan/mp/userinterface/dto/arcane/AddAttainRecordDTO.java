package site.minnan.mp.userinterface.dto.arcane;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 岛球获取记录添加参数
 *
 * @author Minnan on 2022/04/13
 */
@Data
public class AddAttainRecordDTO {

    /**
     * 添加日期
     */
    private String noteDate;

    /**
     * 获取岛球列表
     */
    private List<AttainItem> attainList;

    public List<AttainItem> getAttainList(){
        return attainList.stream()
                .filter(AttainItem::isFill)
                .collect(Collectors.toList());
    }
}
