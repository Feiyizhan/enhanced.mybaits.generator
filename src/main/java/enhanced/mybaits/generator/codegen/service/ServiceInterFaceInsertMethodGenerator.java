/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.codegen.service;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstractServiceInterfaceMethodGenerator;
import enhanced.mybaits.generator.enums.ServiceMethodEnum;

/**
 * Service 接口新增方法生成器
 * @author 徐明龙 XuMingLong 
 * @createDate 2018-11-20 
 */
public class ServiceInterFaceInsertMethodGenerator extends AbstractServiceInterfaceMethodGenerator {

    public ServiceInterFaceInsertMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 增加方法参数
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     * @param method
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
     * @createDate 2018-11-21 
     * @param method
     */
    @Override
    protected void setMethodReturnType(Method method) {
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(getStandardCheckAndHandleDtoClassName());
        returnType.addTypeArgument(PrimitiveTypeWrapper.getIntegerInstance());
        method.setReturnType(returnType);
        this.mixedContext.getServiceInterface().addImportedType(new FullyQualifiedJavaType(returnType.getFullyQualifiedNameWithoutTypeParameters()));
        
    }

    /**
     * 获取Service方法名
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-23 
     * @return
     */
    @Override
    protected ServiceMethodEnum getServiceMethod() {
        return ServiceMethodEnum.INSERT;
    }

    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-23 
     * @return
     */
    @Override
    protected String calculateMethodName() {
        return ServiceMethodEnum.INSERT.getValue();
    }


}
