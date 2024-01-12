
package enhanced.mybaits.generator.codegen.service;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.enums.ServiceMethodEnum;

/**
 * Service 接口新增方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class ServiceInterfaceInsertMethodGenerator extends AbstractServiceInterfaceMethodGenerator {

    public ServiceInterfaceInsertMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 设置返回参数
     * @author 徐明龙 XuMingLong 
     * @param method 待处理的方法
     */
    @Override
    protected void addMethodParameter(Method method) {
        FullyQualifiedJavaType formType = this.mixedContext.getFormClass().getType();
        Parameter formParameter = new Parameter(formType,
            formParameterName);
        method.addParameter(formParameter);
        this.mixedContext.getServiceInterface().addImportedType(formType);
        
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
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(getStandardCheckAndHandleDtoClassName());
        returnType.addTypeArgument(PrimitiveTypeWrapper.getIntegerInstance());
        method.setReturnType(returnType);
        this.mixedContext.getServiceInterface().addImportedType(new FullyQualifiedJavaType(returnType.getFullyQualifiedNameWithoutTypeParameters()));
        
    }

    /**
     * 获取Service方法对应的枚举
     * @author 徐明龙 XuMingLong 
     * @return 返回方法对应的枚举
     */
    @Override
    protected ServiceMethodEnum getServiceMethod() {
        return ServiceMethodEnum.INSERT;
    }

    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong 
     * @return 计算方法名称
     */
    @Override
    protected String calculateMethodName() {
        return ServiceMethodEnum.INSERT.getValue();
    }


}
