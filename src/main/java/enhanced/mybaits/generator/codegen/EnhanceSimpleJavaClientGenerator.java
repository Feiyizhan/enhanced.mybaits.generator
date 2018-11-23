/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。 
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.codegen;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.SimpleJavaClientGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.config.PropertyRegistry;

import enhanced.mybaits.generator.codegen.javamapper.GetByPrimaryKeyMethodGenerator;
import enhanced.mybaits.generator.codegen.javamapper.ListAllMethodGenerator;


/**
 * 增强的DAO 接口生成器
 * 
 * @author 徐明龙 XuMingLong
 * @createDate 2018-11-16
 */
public class EnhanceSimpleJavaClientGenerator extends SimpleJavaClientGenerator {
    

    /**
     * 生成DAO 接口
     * 
     * @author 徐明龙 XuMingLong
     * @createDate 2018-11-16
     * @return
     */
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        //获取Java Client
        Interface interfaze = getJavaClient();
        if (context.getPlugins().clientGenerated(interfaze, null, introspectedTable)) {
            answer.add(interfaze);
        }
        
        List<CompilationUnit> extraCompilationUnits = getExtraCompilationUnits();
        if (extraCompilationUnits != null) {
            answer.addAll(extraCompilationUnits);
        }
        
        return answer;
    }
    
    
    

    /**
     * 获取Java Client
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     * @return
     */
    protected Interface getJavaClient() {
        progressCallback.startTask(getString("Progress.17", introspectedTable.getFullyQualifiedTable().toString()));

        CommentGenerator commentGenerator = context.getCommentGenerator();
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
        }

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addMapperClassComment(interfaze, introspectedTable);
        }
        
        String rootInterface = introspectedTable.getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        if (!stringHasValue(rootInterface)) {
            rootInterface =
                context.getJavaClientGeneratorConfiguration().getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        }

        if (stringHasValue(rootInterface)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);
            interfaze.addSuperInterface(fqjt);
            interfaze.addImportedType(fqjt);
        }

        addInsertMethod(interfaze);
        addDeleteByPrimaryKeyMethod(interfaze);
        addUpdateByPrimaryKeyMethod(interfaze);
        addGetByPrimaryKeyMethod(interfaze);
        addGetByPrimaryKeyAndLockedMethod(interfaze);
        addListAllMethod(interfaze);

        return interfaze;
    }
    


    /**
     * 增加获取指定主键对应的记录方法
     * 
     * @author 徐明龙 XuMingLong
     * @createDate 2018-11-16
     * @param interfaze
     */
    protected void addGetByPrimaryKeyMethod(Interface interfaze) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractJavaMapperMethodGenerator methodGenerator = new GetByPrimaryKeyMethodGenerator(false);
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    /**
     * 增加获取指定主键对应的记录并锁定方法
     * 
     * @author 徐明龙 XuMingLong
     * @createDate 2018-11-16
     * @param interfaze
     */
    protected void addGetByPrimaryKeyAndLockedMethod(Interface interfaze) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractJavaMapperMethodGenerator methodGenerator = new GetByPrimaryKeyMethodGenerator(true);
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    /**
     * 增加获取所有记录方法
     * 
     * @author 徐明龙 XuMingLong
     * @createDate 2018-11-16
     * @param interfaze
     */
    protected void addListAllMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new ListAllMethodGenerator();
        initializeAndExecuteGenerator(methodGenerator, interfaze);
    }
    

    
    /**
     * 获取对应的Xml生成器
     * 
     * @author 徐明龙 XuMingLong
     * @createDate 2018-11-16
     * @return
     */
    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator() {
        return new EnhanceSimpleXMLMapperGenerator();
    }
}
