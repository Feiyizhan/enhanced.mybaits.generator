/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.codegen;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import enhanced.mybaits.generator.dom.java.FormClass;
import enhanced.mybaits.generator.dom.java.ResultClass;
import enhanced.mybaits.generator.dom.java.ServiceImplClass;
import enhanced.mybaits.generator.dom.java.ServiceInterface;
import enhanced.mybaits.generator.dom.java.TestsClass;
import enhanced.mybaits.generator.enums.ServiceMethodEnum;

/**
 * 增加的注释生成器接口
 * @author 徐明龙 XuMingLong 
 * @createDate 2018-11-19 
 */
public interface IEnhanceCommentGenerator {

    /**
     * Java Mapper类注释
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     * @param interfaze
     * @param introspectedTable
     */
    void addMapperClassComment(Interface interfaze,IntrospectedTable introspectedTable);
    
    /**
     * Java Mapper 测试类注释
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     * @param testsClass
     * @param introspectedTable
     */
    void addMapperTestsClassComment(TestsClass testsClass,IntrospectedTable introspectedTable);
    
    /**
     * Java Mapper 测试类方法注释
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     * @param method
     */
    void addMapperTestsMethodComment(Method method);

    /**
     * Service接口类注释
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-20 
     * @param serviceInterface
     * @param introspectedTable
     */
    void addServiceInterfaceComment(ServiceInterface serviceInterface, IntrospectedTable introspectedTable);

    /**
     * Form类注释
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-20 
     * @param formClass
     * @param introspectedTable
     */
    void addFormClassComment(FormClass formClass, IntrospectedTable introspectedTable);

    /**
     * Result类注释
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-20 
     * @param resultClass
     * @param introspectedTable
     */
    void addResultClassComment(ResultClass resultClass, IntrospectedTable introspectedTable);

    /**
     * Service接口实现类注释
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-20 
     * @param serviceImplClass
     * @param introspectedTable
     */
    void addServiceImplClassComment(ServiceImplClass serviceImplClass, IntrospectedTable introspectedTable);

    /**
     * 增加注入的Mapper字段的注释
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-20 
     * @param field
     * @param introspectedTable
     */
    void addAutowiredMapperFieldComment(Field field, IntrospectedTable introspectedTable);

    /**
     * Service 方法的注释
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-23 
     * @param method
     * @param methodEnum
     */
    void addServiceMethodComment(Method method,ServiceMethodEnum methodEnum);

    /**
     * Service 扩展的方法的注释
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     * @param method
     */
    void addServiceExtraMethodComment(Method method);
}
