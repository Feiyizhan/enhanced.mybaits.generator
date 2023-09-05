package enhanced.mybaits.generator.codegen;

import enhanced.mybaits.generator.codegen.javamapper.GetByPrimaryKeyMethodGenerator;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.SimpleJavaClientGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.SelectAllMethodGenerator;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;


/**
 * 增强的DAO 接口生成器
 * 
 * @author 徐明龙 XuMingLong
 */
public class EnhanceSimpleJavaClientGenerator extends SimpleJavaClientGenerator {
    

    /**
     * 生成DAO 接口
     * @author 徐明龙 XuMingLong
     * @return 返回生成的接口类
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
     * @return 返回接口类
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
     * 
     * @param interfaze 接口类
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
     * 
     * @param interfaze 接口类
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
     * @param interfaze 指定的接口类
     */
    protected void addListAllMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new SelectAllMethodGenerator();
        initializeAndExecuteGenerator(methodGenerator, interfaze);
    }
    

    
    /**
     * 获取对应的Xml生成器
     * 
     * @author 徐明龙 XuMingLong
     * @return 返回增强的的简单XML Mapper生成器
     */
    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator() {
        return new EnhanceSimpleXMLMapperGenerator();
    }
}
