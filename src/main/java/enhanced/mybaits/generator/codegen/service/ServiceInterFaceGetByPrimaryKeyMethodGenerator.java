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
 * Service 接口获取主键对应的记录方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class ServiceInterFaceGetByPrimaryKeyMethodGenerator extends AbstractServiceInterfaceMethodGenerator {

    public ServiceInterFaceGetByPrimaryKeyMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong 
     * @return
     */
    @Override
    protected String calculateMethodName() {
        String subName = getJoinedKeyColumnListJavaPropertyName("And",true);
        return ServiceMethodEnum.GET_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName);
    }

    /**
     * 增加方法参数
     * @author 徐明龙 XuMingLong 
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
        
    }

    /**
     * 设置返回参数
     * @author 徐明龙 XuMingLong 
     * @param method
     */
    @Override
    protected void setMethodReturnType(Method method) {
        FullyQualifiedJavaType returnType = this.mixedContext.getBaseRecord().getType();
        method.setReturnType(returnType);
        this.mixedContext.getServiceInterface().addImportedType(returnType);
        
    }

    /**
     * 获取Service方法名 
     * @author 徐明龙 XuMingLong 
     * @return
     */
    @Override
    protected ServiceMethodEnum getServiceMethod() {
        return ServiceMethodEnum.GET_BY_PRIMARY_KEY;
    }


}
