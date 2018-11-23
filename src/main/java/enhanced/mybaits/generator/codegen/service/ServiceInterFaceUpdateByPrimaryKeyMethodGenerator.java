/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.codegen.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstractServiceInterfaceMethodGenerator;
import enhanced.mybaits.generator.enums.ServiceMethodEnum;

/**
 * Service 接口更新指定记录方法生成器
 * @author 徐明龙 XuMingLong 
 * @createDate 2018-11-20 
 */
public class ServiceInterFaceUpdateByPrimaryKeyMethodGenerator extends AbstractServiceInterfaceMethodGenerator {

    public ServiceInterFaceUpdateByPrimaryKeyMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     * @return
     */
    @Override
    protected String calculateMethodName() {
        String subName = getJoinedKeyColumnListJavaPropertyName("And",true);
        return ServiceMethodEnum.UPDATE_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName);
    }

    /**
     * 增加方法参数
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     * @param method
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
        FullyQualifiedJavaType returnType = getListFormValidErrorType();
        method.setReturnType(returnType);
        returnType.getImportList().forEach((r)->{
            this.mixedContext.getServiceInterface().addImportedType(new FullyQualifiedJavaType(r));
        });
        
    }

    /**
     * 获取Service方法名 
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-23 
     * @return
     */
    @Override
    protected ServiceMethodEnum getServiceMethod() {
        return ServiceMethodEnum.UPDATE_BY_PRIMARY_KEY;
    }


}
