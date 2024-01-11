
package enhanced.mybaits.generator.codegen;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;

import enhanced.mybaits.generator.dom.java.FormClass;
import enhanced.mybaits.generator.dom.java.ResClass;
import enhanced.mybaits.generator.dom.java.ServiceImplClass;
import enhanced.mybaits.generator.dom.java.ServiceInterface;
import enhanced.mybaits.generator.dom.java.TestsClass;
import enhanced.mybaits.generator.enums.ServiceMethodEnum;

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
     * Form类注释
     * @author 徐明龙 XuMingLong 
     * @param formClass 表单类
     * @param introspectedTable 对应的表 
     */
    void addFormClassComment(FormClass formClass, IntrospectedTable introspectedTable);

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
     * Service 扩展的方法的注释
     * @author 徐明龙 XuMingLong 
     * @param method 基于的方法
     */
    void addServiceExtraMethodComment(Method method);
}
