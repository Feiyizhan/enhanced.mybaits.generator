/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.plugin;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.TableConfiguration;

import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.EnhanceIntrospectedTableMyBatis3SimpleImpl;
import enhanced.mybaits.generator.GeneratorExecutor;
import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.extra.FormGenerator;
import enhanced.mybaits.generator.codegen.extra.ResultGenerator;
import enhanced.mybaits.generator.codegen.extra.SimpleJavaClientTestsGenerator;
import enhanced.mybaits.generator.codegen.service.ServiceInterfaceGenerator;
import enhanced.mybaits.generator.codegen.service.impl.ServiceImplGenerator;

/**
 * Mybaits Generator 增加插件
 * @author 徐明龙 XuMingLong 
 * @createDate 2018-11-09 
 */
public class EnhancePlugin extends PluginAdapter{

    
    /**
     * 混合的上下文对象
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-20 
     */
    private MixedContext mixedContext;
    
    /**
     * 解析好的表
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     */
    private IntrospectedTable table;

   
    /**
     * SQL Mapper文件
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     */
    private Document document;

    private List<String> warnings;
    
    
    /**
     * 生成器的执行器
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-20 
     */
    private GeneratorExecutor generatorExecutor;


    /**
     * Mapper 类生成
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
        IntrospectedTable introspectedTable) {
        this.mixedContext.setMapper(interfaze);
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }


    /**
     * 基础类型生成
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.mixedContext.setBaseRecord(topLevelClass);
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    

    /**
     * Sql Mapper生成
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     * @param document
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        this.document = document;
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }



    /**
     * 补充生成的Java文件
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-16 
     * @param introspectedTable
     * @return
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> answer = new ArrayList<GeneratedJavaFile>();
        //生成Mapper测试类
        generatorExecutor.addJavaGenerator(new SimpleJavaClientTestsGenerator(mixedContext));
        //生成Form类
        generatorExecutor.addJavaGenerator(new FormGenerator(mixedContext));
        //生成Result类
        generatorExecutor.addJavaGenerator(new ResultGenerator(mixedContext));
        //生成Service接口类
        generatorExecutor.addJavaGenerator(new ServiceInterfaceGenerator(mixedContext));
        //生成Service接口实现类
        generatorExecutor.addJavaGenerator(new ServiceImplGenerator(mixedContext));
        answer.addAll(generatorExecutor.generateAllFiles());
        return answer;
    }



    /**
     *
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-16 
     * @param introspectedTable
     * @return
     */
    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
        // TODO Auto-generated method stub
        return super.contextGenerateAdditionalXmlFiles(introspectedTable);
    }


    /**
     *
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-09 
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass,
        IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    
    /**
     *
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-15 
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass,
        IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }
    
    

    /**
     * 插件初始化
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-09 
     * @param introspectedTable
     */
    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        this.table = introspectedTable;
        //获取表的主键生成配置
        TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();
        GeneratedKey gk = tableConfiguration.getGeneratedKey();
        //如果没有主动设置主键生成配置，那么获取默认的主键生成配置
        if(gk==null) {
            gk = getDefaultGeneratedKey(introspectedTable);
            tableConfiguration.setGeneratedKey(gk);
        }
        //设置其他默认值
        tableConfiguration.setDeleteByExampleStatementEnabled(false);
        tableConfiguration.setSelectByExampleStatementEnabled(false);
        tableConfiguration.setUpdateByExampleStatementEnabled(false);
        
        mixedContext = new MixedContext();
        generatorExecutor = new GeneratorExecutor();
        generatorExecutor.setContext(context);
        generatorExecutor.setTable(introspectedTable);
        generatorExecutor.setWarnings(warnings);
        
        
    }
    
    /**
     * 获取默认的主键生成配置
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-15 
     * @param introspectedTable
     * @return
     */
    private GeneratedKey getDefaultGeneratedKey(IntrospectedTable introspectedTable) {
        
        List<IntrospectedColumn> keyColumns = introspectedTable
            .getPrimaryKeyColumns();
        if(CollectionUtils.isEmpty(keyColumns)) {
            return null;
        }
        IntrospectedColumn keyColumn = null;
        //对于usegeneratedkeys来说，只能有一个主键列；
        if (keyColumns.size() == 1) {
            //得到这个唯一的主键列
            keyColumn = keyColumns.get(0);
            //得到这个列映射成Java模型之后的属性对应的Java类型；
            FullyQualifiedJavaType javaType = keyColumn
                    .getFullyQualifiedJavaType();
            //usegeneratedkeys要求主键只能是递增的，所以我们把这个主键属性的类型分别和Integer，Long，Short做对比；
            if (javaType.equals(PrimitiveTypeWrapper.getIntegerInstance())
                    || javaType.equals(PrimitiveTypeWrapper
                            .getLongInstance())
                    || javaType.equals(PrimitiveTypeWrapper
                            .getShortInstance())) {
                //如果是Integer，Long，Short三个类型中的而一个；则允许自动生成主键
                GeneratedKey gk = new GeneratedKey(keyColumn.getActualColumnName(),
                    EnhanceConstant.SQL_MAPPER_GENERATOR_DEFAULT_KEY_SQL_STATEMENT,
                    false, null);
                return gk;
            }
        }
        return null;
    }


    /**
     * 参数校验
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-09 
     * @param warnings
     * @return
     */
    @Override
    public boolean validate(List<String> warnings) {
        this.warnings = warnings;
        if(this.context.getTargetRuntime().equals(EnhanceIntrospectedTableMyBatis3SimpleImpl.class.getName())) {
            return true;
        }
        return false;
    }

}
