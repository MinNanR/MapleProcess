package site.minnan.mp.userinterface.dto.character;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加角色参数
 *
 * @author Minnan on 2022/04/05
 */
@Data
public class AddCharacterDTO {

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String characterName;

    /**
     * 职业
     */
    @NotBlank(message = "职业不能为空")
    private String job;

    /**
     * 等级
     */
    @NotNull(message = "等级不能为空")
    private Integer level;


}
