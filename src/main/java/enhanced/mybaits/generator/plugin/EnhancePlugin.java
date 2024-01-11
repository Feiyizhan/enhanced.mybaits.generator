
package enhanced.mybaits.generator.plugin;

import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.EnhanceIntrospectedTableMyBatis3SimpleImpl;
import enhanced.mybaits.generator.GeneratorExecutor;
import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.test.*;
import enhanced.mybaits.generator.codegen.model.DOGenerator;
import enhanced.mybaits.generator.codegen.model.DTOGenerator;
import enhanced.mybaits.generator.codegen.model.FormGenerator;
import enhanced.mybaits.generator.codegen.model.ResGenerator;
import enhanced.mybaits.generator.codegen.service.ServiceInterfaceGenerator;
import enhanced.mybaits.generator.codegen.service.impl.ServiceImplGenerator;
import enhanced.mybaits.generator.enums.EnhanceSqlIdEnum;
import enhanced.mybaits.generator.repository.RepositoryInterfaceGenerator;
import enhanced.mybaits.generator.repository.impl.RepositoryImplGenerator;
import org.apache.commons.collections4.CollectionUtils;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Mybaits Generator 增加插件
 * @author 徐明龙 XuMingLong 
 */
public class EnhancePlugin extends PluginAdapter{

    
    /**
     * 混合的上下文对象
     * @author 徐明龙 XuMingLong 
     */
    private MixedContext mixedContext;
    
    /**
     * 解析好的表
     * @author 徐明龙 XuMingLong 
     */
    private IntrospectedTable table;

   
    /**
     * SQL Mapper文件
     * @author 徐明龙 XuMingLong 
     */
    private Document document;

    private List<String> warnings;
    
    
    /**
     * 生成器的执行器
     * @author 徐明龙 XuMingLong 
     */
    private GeneratorExecutor generatorExecutor;


    /**
     * Mapper 类代码已生成
     * @author 徐明龙 XuMingLong 
     * @param interfaze Mapper接口类
     * @param topLevelClass 基础类
     * @param introspectedTable 解析好的表
     * @return 是否保留该代码
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
        IntrospectedTable introspectedTable) {
        this.mixedContext.setMapper(interfaze);
        return true;
    }


    /**
     * 基础类型代码已生成
     * @author 徐明龙 XuMingLong 
     * @param topLevelClass 基础类
     * @param introspectedTable 解析好的表
     * @return 是否保留该代码
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.mixedContext.setBaseRecord(topLevelClass);
        //增加Lombok注释
        addLombokAnnotation(topLevelClass);
        return true;
    }

    /**
     * 增加Lombok注解
     * @author 徐明龙 XuMingLong 
     * @param topLevelClass 实体类
     */
    protected void addLombokAnnotation(TopLevelClass topLevelClass) {
        topLevelClass.addImportedType("lombok.*");
        topLevelClass.addAnnotation("@Data");
    }
    

    /**
     * Sql Mapper生成
     * @author 徐明龙 XuMingLong 
     * @param document XML文档
     * @param introspectedTable  解析好的表
     * @return Sql Mapper生成
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        this.document = document;
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }



    /**
     * 补充生成的Java文件
     * @author 徐明龙 XuMingLong 
     * @param introspectedTable 解析好的表
     * @return 补充生成的Java文件
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> answer = new ArrayList<>();
        if(canGenerateSimpleJavaClientTestClass()){
            //生成Mapper测试类
            generatorExecutor.addJavaGenerator(new SimpleJavaClientTestsGenerator(mixedContext));
        }
        //生成Form类
        generatorExecutor.addJavaGenerator(new FormGenerator(mixedContext));
        //生成Result类
        generatorExecutor.addJavaGenerator(new ResGenerator(mixedContext));
        //生成DO类型
        generatorExecutor.addJavaGenerator(new DOGenerator(mixedContext));
        //生成DTO类型
        generatorExecutor.addJavaGenerator(new DTOGenerator(mixedContext));

        if(canGenerateServiceClass()){
            //生成Service接口类
            generatorExecutor.addJavaGenerator(new ServiceInterfaceGenerator(mixedContext));
            //生成Service接口实现类
            generatorExecutor.addJavaGenerator(new ServiceImplGenerator(mixedContext));
        }
        if(canGenerateRepositoryClass()){
            //生成Repository接口类
            generatorExecutor.addJavaGenerator(new RepositoryInterfaceGenerator(mixedContext));
            //生成Repository接口实现类
            generatorExecutor.addJavaGenerator(new RepositoryImplGenerator(mixedContext));
        }
        answer.addAll(generatorExecutor.generateAllFiles());
        return answer;
    }


    /**
     * 检查是否生成Map测试代码
     * @author 徐明龙 XuMingLong 2022-04-17
     * @return boolean
     */
    private boolean canGenerateSimpleJavaClientTestClass() {
        return StringUtility.stringHasValue(
            this.context
                .getJavaClientGeneratorConfiguration()
                .getProperty(EnhanceConstant.EXTRA_TEST_TARGET_PROJECT_KEY));
    }


    /**
     * 检查是否生成Service代码
     * @author 徐明龙 XuMingLong 2022-04-17
     * @return boolean
     */
    private boolean canGenerateServiceClass() {
        return StringUtility.stringHasValue(
            this.context
                .getJavaClientGeneratorConfiguration()
                .getProperty(EnhanceConstant.EXTRA_SERVICE_TARGET_PACKAGE_KEY));
    }

    /**
     * 检查是否生成Repository代码
     * @author 徐明龙 XuMingLong 2022-04-17
     * @return boolean
     */
    private boolean canGenerateRepositoryClass() {
        return StringUtility.stringHasValue(
            this.context
                .getJavaClientGeneratorConfiguration()
                .getProperty(EnhanceConstant.EXTRA_REPOSITORY_TARGET_PACKAGE_KEY));
    }



    /**
     * XML文件代码已生成
     * @author 徐明龙 XuMingLong 
     * @param introspectedTable 解析好的表
     * @return 补充生成的XML文件
     */
    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
        return super.contextGenerateAdditionalXmlFiles(introspectedTable);
    }


    /**
     * 基础类Set方法代码已生成
     * @author 徐明龙 XuMingLong 
     * @param method 方法名称
     * @param topLevelClass 基础类
     * @param introspectedColumn 字段名
     * @param introspectedTable 解析好的表
     * @param modelClassType 基础类类型
     * @return 是否保留该方法
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass,
        IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    
    /**
     * 基础类Get方法代码已生成
     * @author 徐明龙 XuMingLong 
     * @param method 方法名称
     * @param topLevelClass 基础类
     * @param introspectedColumn 字段名
     * @param introspectedTable 解析好的表
     * @param modelClassType 基础类类型
     * @return 是否保留该方法
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass,
        IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }
    
    

    /**
     * 插件初始化
     * @author 徐明龙 XuMingLong 
     * @param introspectedTable 解析好的表
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
     * @param introspectedTable 解析好的表
     * @return 默认的主键生成配置
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
            if(!keyColumn.isAutoIncrement()) {
                return null;
            }
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
     * @param warnings 额外的参数
     * @return 校验结果
     */
    @Override
    public boolean validate(List<String> warnings) {
        this.warnings = warnings;
        if(this.context.getTargetRuntime().equals(EnhanceIntrospectedTableMyBatis3SimpleImpl.class.getName())) {
            return true;
        }
        return false;
    }


    /**
     * Mapper生成Insert方法后的处理
     * @author 徐明龙 XuMingLong 2023-09-05
     * @param method
     * @param interfaze
     * @param introspectedTable
     * @return boolean
     */
    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze,
        IntrospectedTable introspectedTable) {
        method.setName(EnhanceSqlIdEnum.resolveByOldValue(method.getName()).getValue());
        return true;
    }


    /**
     * Mapper生成selectAll方法后的处理
     * @author 徐明龙 XuMingLong 2023-09-05
     * @param method
     * @param interfaze
     * @param introspectedTable
     * @return boolean
     */
    @Override
    public boolean clientSelectAllMethodGenerated(Method method,
        Interface interfaze, IntrospectedTable introspectedTable) {
        method.setName(EnhanceSqlIdEnum.resolveByOldValue(method.getName()).getValue());
        return true;
    }
}
