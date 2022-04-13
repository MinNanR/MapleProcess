package site.minnan.mp.userinterface.dto.character;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 查询角色信息参数
 *
 * @author Minnan on 2022/04/05
 */
@Data
public class QueryCharacterInfoDTO {

    /**
     * 角色名称
     */
    @NotEmpty(message = "请输入角色名称")
    private String characterName;
}
