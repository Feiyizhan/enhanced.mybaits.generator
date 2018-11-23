/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.codegen;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;

import enhanced.mybaits.generator.MixedContext;

/**
 * 增Java Mapper 测试类方法生成器基类
 * @author 徐明龙 XuMingLong 
 * @createDate 2018-11-19 
 */
public abstract class AbstractMapperTestsMethodGenerator extends AbstractMethodGenerator {
    
    /**
     * 测试类中注入的被测试的Mapper对象
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     */
    protected Field testedMapperField;
    
    /**
     * 被测试的方法
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     */
    protected Method testedMethod;
    
    
    public AbstractMapperTestsMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
        // 设置测试类中注入的被测试的Mapper对象
        setTestedMapperField();
    }

    /**
     * 设置被测试的方法
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     */
    protected abstract void setTestedMethod();

    
    /**
     * 增加方法注解
     * @author 徐明龙 XuMingLong
     * @createDate 2018-11-19
     * @param method
     */
    protected void addMethodAnnotations(Method method) {
        method.addAnnotation("@Test");
    }
    
    /**
     * 增加测试的引入
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     * @param topLevelClass
     */
    protected void addTestsClassImport() {
        mixedContext.getTestsClass().addImportedType("org.junit.Test");
    }
    
    /**
     * 设置引入的被测试Mapper的
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     * @return
     */
    protected void setTestedMapperField() {
        List<Field> list = mixedContext.getTestsClass().getFields();
        for(Field r: list) {
            if(r.getType().equals(mixedContext.getMapper().getType())) {
               this.testedMapperField =  r;
            }
        }
    }
    
    /**
     * 增加测试类对应的测试方法
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     * @param topLevelClass
     */
    public void addMethod() {
        //设置被测试的方法
        setTestedMethod();
        if(testedMethod==null) {
            return;
        }
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(String.join("", "test",StringUtils.capitalize(testedMethod.getName())));
        //增加方法的注解及引入
        addMethodAnnotations(method);
        addTestsClassImport();
        //增加测试方法内容
        addMethodBody(method);
        //生成注释
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        CommentGenerator commentGenerator = context.getCommentGenerator();
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
            enhanceCommentGenerator.addMapperTestsMethodComment(method);
        }
        mixedContext.getTestsClass().addMethod(method);
        
    }
    
    /**
     * 增加测试方法内容
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     * @param method
     */
    protected abstract void addMethodBody(Method method) ;
}
