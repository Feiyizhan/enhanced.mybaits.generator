/**
 *
 */
package enhanced.mybaits.generator.example;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 标准的校验和处理结果对象
 * @author 徐明龙 XuMingLong 2018-11-29
 * @param <T>
 */
@Getter@Setter@ToString@EqualsAndHashCode
public class StandardCheckAndHandleDTO<T> {

    /**
     * 校验结果
     * @author 徐明龙 XuMingLong 2018-11-29
     */
    private List<FormValidError> errors;

    /**
     * 处理结果
     * @author 徐明龙 XuMingLong 2018-11-29
     */
    private T result;

}
