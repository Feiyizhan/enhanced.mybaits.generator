
package enhanced.mybaits.generator;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.Interface;

/**
 * 代码生成工具类
 * @author 徐明龙 XuMingLong 
 */
public class CodeGeneratorUtils {

    /**
     * 获取接口类的变量名
     * @author 徐明龙 XuMingLong
     * @param interfaze
     * @return java.lang.String
     */
    public static String getInterfaceVarName(Interface interfaze){
        return StringUtils.uncapitalize(StringUtils.substring(interfaze.getType().getShortName(),1));
    }
}
