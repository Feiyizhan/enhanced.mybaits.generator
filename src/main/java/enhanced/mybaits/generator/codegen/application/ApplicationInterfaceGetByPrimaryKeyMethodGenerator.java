
package enhanced.mybaits.generator.codegen.application;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.enums.ApplicationMethodEnum;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.List;

/**
 * Application 接口获取主键对应的记录方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class ApplicationInterfaceGetByPrimaryKeyMethodGenerator extends AbstractApplicationInterfaceMethodGenerator {

    public ApplicationInterfaceGetByPrimaryKeyMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }


    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong 
     * @return 返回计算后的方法名称
     */
    @Override
    protected String calculateMethodName() {
        String subName = getJoinedKeyColumnListJavaPropertyName("And",true);
        return ApplicationMethodEnum.GET_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName);
    }

    /**
     * 增加方法参数
     * @author 徐明龙 XuMingLong 
     * @param method 待处理的方法
     */
    @Override
    protected void addMethodParameter(Method method) {
        List<IntrospectedColumn> keyColumnList = this.introspectedTable.getPrimaryKeyColumns();
        keyColumnList.forEach((r)->{
            FullyQualifiedJavaType parameterType = r.getFullyQualifiedJavaType();
            Parameter parameter = new Parameter(parameterType,
                StringUtils.uncapitalize(r.getJavaProperty()));
            method.addParameter(parameter);
            this.mixedContext.getApplicationInterface().addImportedType(parameterType);
            
        });
        
    }

    /**
     * 设置返回参数
     * @author 徐明龙 XuMingLong 
     * @param method 待处理的方法
     */
    @Override
    protected void setMethodReturnType(Method method) {
        FullyQualifiedJavaType returnType = this.mixedContext.getDTOClass().getType();
        method.setReturnType(returnType);
        this.mixedContext.getApplicationInterface().addImportedType(returnType);
        
    }

    /**
     * 获取Application方法对应的枚举
     * @author 徐明龙 XuMingLong 
     * @return 返回方法对应的枚举
     */
    @Override
    protected ApplicationMethodEnum getApplicationMethod() {
        return ApplicationMethodEnum.GET_BY_PRIMARY_KEY;
    }


}
