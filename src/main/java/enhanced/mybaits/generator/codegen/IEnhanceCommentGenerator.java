
package enhanced.mybaits.generator.codegen;

import enhanced.mybaits.generator.dom.java.*;
import enhanced.mybaits.generator.enums.DomainMethodEnum;
import enhanced.mybaits.generator.enums.RepositoryMethodEnum;
import enhanced.mybaits.generator.enums.ServiceMethodEnum;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;

/**
 * 增加的注释生成器接口
 * @author 徐明龙 XuMingLong 
 */
public interface IEnhanceCommentGenerator {

    /**
     * Java Mapper类注释
     * @author 徐明龙 XuMingLong 
     * @param interfaze Mapper接口类
     * @param introspectedTable 对应的表 
     */
    void addMapperClassComment(Interface interfaze,IntrospectedTable introspectedTable);
    
    /**
     * Java Mapper 测试类注释
     * @author 徐明龙 XuMingLong 
     * @param testsClass 测试类
     * @param introspectedTable 对应的表 
     */
    void addMapperTestsClassComment(TestsClass testsClass,IntrospectedTable introspectedTable);
    
    /**
     * Java Mapper 测试类方法注释
     * @author 徐明龙 XuMingLong 
     * @param method 方法类
     */
    void addMapperTestsMethodComment(Method method);

    /**
     * Service接口类注释
     * @author 徐明龙 XuMingLong 
     * @param serviceInterface Service接口类
     * @param introspectedTable 对应的表 
     */
    void addServiceInterfaceComment(ServiceInterface serviceInterface, IntrospectedTable introspectedTable);

    /**
     * Repository接口类注释
     * @author 徐明龙 XuMingLong
     * @param repositoryInterface Repository接口类
     * @param introspectedTable 对应的表
     */
    void addRepositoryInterfaceComment(RepositoryInterface repositoryInterface, IntrospectedTable introspectedTable);

    /**
     * Domain 接口类注释
     * @author 徐明龙 XuMingLong
     * @param domainInterface (Domain接口类
     * @param introspectedTable 对应的表
     */
    void addDomainInterfaceComment(DomainInterface domainInterface, IntrospectedTable introspectedTable);


    /**
     * Form类注释
     * @author 徐明龙 XuMingLong 
     * @param formClass 表单类
     * @param introspectedTable 对应的表 
     */
    void addFormClassComment(FormClass formClass, IntrospectedTable introspectedTable);

    /**
     * DO类注释
     * @author 徐明龙 XuMingLong
     * @param dOClass 数据对象类
     * @param introspectedTable 对应的表
     */
    void addDOClassComment(DOClass dOClass, IntrospectedTable introspectedTable);

    /**
     * DTO类注释
     * @author 徐明龙 XuMingLong
     * @param dTOClass DTO类
     * @param introspectedTable 对应的表
     */
    void addDTOClassComment(DTOClass dTOClass, IntrospectedTable introspectedTable);

    /**
     * Result类注释
     * @author 徐明龙 XuMingLong 
     * @param resultClass 返回结果类
     * @param introspectedTable 对应的表 
     */
    void addResultClassComment(ResClass resultClass, IntrospectedTable introspectedTable);

    /**
     * Service接口实现类注释
     * @author 徐明龙 XuMingLong 
     * @param serviceImplClass Service的实现类
     * @param introspectedTable 对应的表 
     */
    void addServiceImplClassComment(ServiceImplClass serviceImplClass, IntrospectedTable introspectedTable);

    /**
     * Repository接口实现类注释
     * @author 徐明龙 XuMingLong
     * @param repositoryImplClass Repository的实现类
     * @param introspectedTable 对应的表
     */
    void addRepositoryImplClassComment(RepositoryImplClass repositoryImplClass, IntrospectedTable introspectedTable);

    /**
     * Domain接口实现类注释
     * @author 徐明龙 XuMingLong
     * @param domainImplClass Domain的实现类
     * @param introspectedTable 对应的表
     */
    void addDomainImplClassComment(DomainImplClass domainImplClass, IntrospectedTable introspectedTable);

    /**
     * 增加注入的Mapper字段的注释
     * @author 徐明龙 XuMingLong 
     * @param field 注入的字段类
     * @param introspectedTable 对应的表 
     */
    void addAutowiredMapperFieldComment(Field field, IntrospectedTable introspectedTable);

    /**
     * Service 方法的注释
     * @author 徐明龙 XuMingLong 
     * @param method 方法类
     * @param methodEnum 方法的枚举
     */
    void addServiceMethodComment(Method method,ServiceMethodEnum methodEnum);

    /**
     * Repository 方法的注释
     * @author 徐明龙 XuMingLong
     * @param method 方法类
     * @param repositoryMethod 方法的枚举
     */
    void addRepositoryMethodComment(Method method, RepositoryMethodEnum repositoryMethod);

    /**
     * Domain 方法的注释
     * @author 徐明龙 XuMingLong
     * @param method 方法类
     * @param domainMethod 方法的枚举
     */
    void addDomainMethodComment(Method method, DomainMethodEnum domainMethod);

    /**
     * Service 扩展的方法的注释
     * @author 徐明龙 XuMingLong 
     * @param method 基于的方法
     */
    void addServiceExtraMethodComment(Method method);

    /**
     * Domain 扩展的方法的注释
     * @author 徐明龙 XuMingLong
     * @param method 基于的方法
     */
    void addDomainExtraMethodComment(Method method);
}
