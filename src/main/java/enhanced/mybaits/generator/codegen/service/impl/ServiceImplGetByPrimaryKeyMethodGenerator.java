/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.codegen.service.impl;


import java.util.List;

import org.mybatis.generator.api.dom.java.Method;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstractServiceImplMethodGenerator;
import enhanced.mybaits.generator.enums.EnhanceSqlIdEnum;
import enhanced.mybaits.generator.enums.ServiceMethodEnum;

/**
 * Service 接口实现类获取主键对应的记录方法生成器
 * @author 徐明龙 XuMingLong 
 * @createDate 2018-11-21 
 */
public class ServiceImplGetByPrimaryKeyMethodGenerator extends AbstractServiceImplMethodGenerator {

    public ServiceImplGetByPrimaryKeyMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 增加方法内容
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     * @param method
     */
    @Override
    protected void addMethodBody(Method method) {
        
        StringBuilder sb = new StringBuilder();
        //增加返回结果
        sb.append("return ");
        sb.append(mapperFieldName);
        sb.append(".");
        sb.append(EnhanceSqlIdEnum.GET_BY_PRIMARY_KEY.getValue());
        sb.append("(");
        sb.append(getJoinedKeyColumnListJavaPropertyName(",",false));
        sb.append(");");
        method.addBodyLine(sb.toString());
        sb.setLength(0);
    }

    /**
     * 增加额外的方法
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     * @param method
     * @return
     */
    @Override
    protected List<Method> addExtraMethod(Method method) {
        return null;
    }


    /**
     * 返回Service方法枚举类
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-23 
     * @return
     */
    @Override
    protected ServiceMethodEnum getServiceMethod() {
        return ServiceMethodEnum.GET_BY_PRIMARY_KEY;
    }

    
    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong
     * @createDate 2018-11-23
     * @return
     */
    @Override
    protected String calculateMethodName() {
        String subName = getJoinedKeyColumnListJavaPropertyName("And",true);
        return ServiceMethodEnum.GET_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName);
    }
}
