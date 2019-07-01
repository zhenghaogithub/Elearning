package org.darod.elearning.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/20 0020 9:20
 */
@Data
public class UserModel implements Serializable {
    private Integer id;
    @NotBlank(message = "用户名不能为空")
    private String name;
    @NotNull(message = "性别不能为空")
    private Byte gender;
    @NotNull(message = "年龄不能为空")
    @Min(value = 0,message = "年龄必须大于0岁")
    @Max(value = 150,message = "年龄不能大于150岁")
    private Integer age;
    @NotBlank(message = "手机号不能为空")
    private String telphone;
    private String registerMode;
    private String thirdPartyId;
    @NotBlank(message = "密码不能为空")
    @JSONField(serialize = false)
    private String encryptPassword;

    private String email;
    @NotNull(message = "头像URL不能为空")
    @URL(message = "URL格式不正确")
    private String headUrl;
    @NotNull(message = "用户状态不能为空")
    private Byte userState;
}
