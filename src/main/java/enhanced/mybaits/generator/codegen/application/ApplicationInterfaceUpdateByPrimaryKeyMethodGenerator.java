
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
 * Application 接口更新指定记录方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class ApplicationInterfaceUpdateByPrimaryKeyMethodGenerator extends AbstractApplicationInterfaceMethodGenerator {

    public ApplicationInterfaceUpdateByPrimaryKeyMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }


    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong 
     * @return 返回计算的方法名称
     */
    @Override
    protected String calculateMethodName() {
        String subName = getJoinedKeyColumnListJavaPropertyName("And",true);
        return ApplicationMethodEnum.UPDATE_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName);
    }

    /**
     * 增加方法参数
     * @author 徐明龙 XuMingLong 
     * @param method 基于的方法
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

        FullyQualifiedJavaType dTOType = this.mixedContext.getDTOClass().getType();
        Parameter dTOParameter = new Parameter(dTOType, dTOVarName);
        method.addParameter(dTOParameter);
        this.mixedContext.getApplicationInterface().addImportedType(dTOType);

        FullyQualifiedJavaType userType = new FullyQualifiedJavaType(getUserClassName());
        Parameter userParameter = new Parameter(userType, userVarName);
        method.addParameter(userParameter);
        this.mixedContext.getApplicationInterface().addImportedType(userType);
    }

    /**
     * 设置返回参数
     * @author 徐明龙 XuMingLong 
     * @param method 基于的方法
     */
    @Override
    protected void setMethodReturnType(Method method) {
        FullyQualifiedJavaType returnType = getListFormValidErrorType();
        method.setReturnType(returnType);
        returnType.getImportList().forEach((r)->{
            this.mixedContext.getApplicationInterface().addImportedType(new FullyQualifiedJavaType(r));
        });
    }

    /**
     * 获取Application方法名枚举类
     * @author 徐明龙 XuMingLong 
     * @return 返回方法对应的枚举
     */
    @Override
    protected ApplicationMethodEnum getApplicationMethod() {
        return ApplicationMethodEnum.UPDATE_BY_PRIMARY_KEY;
    }


}
