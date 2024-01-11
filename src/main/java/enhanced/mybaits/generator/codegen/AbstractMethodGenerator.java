
package enhanced.mybaits.generator.codegen;

import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.MixedContext;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.codegen.AbstractGenerator;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 方法生成器基类
 * @author 徐明龙 XuMingLong 
 */
public abstract class AbstractMethodGenerator extends AbstractGenerator {
    /**
     * 混合的上下文对象
     * @author 徐明龙 XuMingLong 
     */
    protected MixedContext mixedContext;
    
    public AbstractMethodGenerator(MixedContext mixedContext) {
        this.mixedContext = mixedContext;
    }
    
    /**
     * 增加方法
     * @author 徐明龙 XuMingLong 
     */
    public abstract void addMethod() ;
    
    /** 
     * 获取拼接的关键字列表Java字段名称
     * @author 徐明龙 XuMingLong 
     * @param joinStr 连接的字符串
     * @param convertToCapitalize 是否转换为首字母大写的驼峰命名
     * @return 拼接的关键字列表Java字段名称
     */
    protected String getJoinedKeyColumnListJavaPropertyName(String joinStr,boolean convertToCapitalize) {
        List<String> nameList = getKeyColumnJavaPropertyNameList() ;
        if(convertToCapitalize) {
            nameList = nameList.stream()
                .map(StringUtils::capitalize)
                .collect(Collectors.toList());
        }
        return StringUtils.join(nameList, joinStr);
            
    }
    
    /**
     * 返回自增字段的Java属性名
     * @author 徐明龙 XuMingLong 2018-11-27 
     * @return 返回自增字段的Java属性名
     */
    protected String getAutoIncrementKeyName() {
        if(this.introspectedTable.getGeneratedKey()!=null) {
            return JavaBeansUtil.getValidPropertyName(this.introspectedTable.getGeneratedKey().getColumn());
        }
        return null;
    }
    
    /**
     * 获取关键字的名称列表
     * @author 徐明龙 XuMingLong 
     * @return 返回关键字的Java属性名称列表
     */
    protected List<String> getKeyColumnJavaPropertyNameList(){
        return this.introspectedTable.getPrimaryKeyColumns()
            .stream()
            .map(IntrospectedColumn::getJavaProperty)
            .collect(Collectors.toList());
    }

    
    /**
     * 获取用户类名参数
     * @author 徐明龙 XuMingLong 
     * @return 返回用户类名
     */
    protected String getUserClassName() {
        return this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.USER_CLASS_NAME_KEY);
    }

    /**
     * 获取日期类名
     * @author 徐明龙 XuMingLong
     * @return 返回时间类名
     */
    protected String getNowClassName() {
        return LocalDateTime.class.getName();
    }

    /**
     * 获取ID类名
     * @author 徐明龙 XuMingLong
     * @return 返回时间类名
     */
    protected String getIdClassName() {
        return this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.ID_CLASS_NAME_KEY);
    }
    
    /**
     * 获取标准的校验和处理结果对象
     * @author 徐明龙 XuMingLong 
     * @return 返回标准的检查和处理类对象名称
     */
    protected String getStandardCheckAndHandleDtoClassName() {
        return this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.STANDARD_CHECK_AND_HANDLE_DTO_CLASS_NAME_KEY);
    }
    
    /**
     * 获取表单验证失败内容参数对象
     * @author 徐明龙 XuMingLong 
     * @return 返回表单内容校验结果对象类名
     */
    protected String getFormValidErrorClassName() {
        return this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.FORM_VALID_ERROR_CLASS_NAME_KEY);
    }

    /**
     * 生成字段复制的代码字段信息代码
     * @author 徐明龙 XuMingLong
     * @param leftField 左边的字段
     * @param rightField 右边的字段
     * @param leftVarName 左边的变量名
     * @param rightVarName 右边的变量名
     */
    protected String generateCopyFieldCode(Field leftField,Field rightField,String leftVarName,String rightVarName) {
        return StringUtils.join(
            leftVarName,
            ".set",
            StringUtils.capitalize(leftField.getName()),
            "(",
            rightVarName,
            ".get",
            StringUtils.capitalize(rightField.getName()),
            "());"
        );
    }
}
