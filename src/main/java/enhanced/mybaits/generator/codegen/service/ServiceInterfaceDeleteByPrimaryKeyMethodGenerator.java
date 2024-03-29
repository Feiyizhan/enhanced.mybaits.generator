
package enhanced.mybaits.generator.codegen.service;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.enums.ServiceMethodEnum;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.List;

/**
 * Service 接口删除指定记录记录方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class ServiceInterfaceDeleteByPrimaryKeyMethodGenerator extends AbstractServiceInterfaceMethodGenerator {

    public ServiceInterfaceDeleteByPrimaryKeyMethodGenerator(MixedContext mixedContext) {
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
        return ServiceMethodEnum.DELETE_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName);
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
            this.mixedContext.getServiceInterface().addImportedType(parameterType);
        });
        
        FullyQualifiedJavaType userType = new FullyQualifiedJavaType(getUserClassName());
        Parameter userParameter = new Parameter(userType,
            userParameterName);
        method.addParameter(userParameter);
        this.mixedContext.getServiceInterface().addImportedType(userType);
        
    }

    /**
     * 设置返回参数
     * @author 徐明龙 XuMingLong 
     * @param method 待处理的方法
     */
    @Override
    protected void setMethodReturnType(Method method) {
        FullyQualifiedJavaType returnType = getListFormValidErrorType();
        method.setReturnType(returnType);
        returnType.getImportList().forEach((r)->{
            this.mixedContext.getServiceInterface().addImportedType(new FullyQualifiedJavaType(r));
        });
        
    }

    /**
     * 获取Service方法对应的枚举
     * @author 徐明龙 XuMingLong 
     * @return 返回方法对应的枚举
     */
    @Override
    protected ServiceMethodEnum getServiceMethod() {
        return ServiceMethodEnum.DELETE_BY_PRIMARY_KEY;
    }


}
