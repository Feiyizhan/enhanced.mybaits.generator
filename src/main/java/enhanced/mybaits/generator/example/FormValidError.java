/**
 *
 */
package enhanced.mybaits.generator.example;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 表单验证失败内容
 * @author 徐明龙 XuMingLong 2018-11-29
 */
@Getter@Setter@ToString@EqualsAndHashCode
public class FormValidError{


    /**
     * 校验的表单字段名
     * @author 徐明龙 XuMingLong 2018-11-29
     */
    private String field;

    /**
     * 字段校验失败的描述
     * @author 徐明龙 XuMingLong 2018-11-29
     */
    private String message;

    public FormValidError(String field, String message) {
        this.field=field;
        this.message=message;
    }
    public FormValidError() {
        super();
    }

}
