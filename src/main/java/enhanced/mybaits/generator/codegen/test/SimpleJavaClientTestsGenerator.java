
package enhanced.mybaits.generator.codegen.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;

import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstractMethodGenerator;
import enhanced.mybaits.generator.codegen.AbstratEnhanceJavaGenerator;
import enhanced.mybaits.generator.codegen.IEnhanceCommentGenerator;
import enhanced.mybaits.generator.dom.java.TestsClass;

/**
 * 简单的Java Client 测试类生成器
 * @author 徐明龙 XuMingLong 
 */
public class SimpleJavaClientTestsGenerator extends AbstratEnhanceJavaGenerator{

    public SimpleJavaClientTestsGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 生成测试类
     * @author 徐明龙 XuMingLong 
     * @return Java Client 测试类
     */
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        //获取Java Client测试类
        TestsClass testsClass = getJavaClientTests();
        answer.add(testsClass);
        
        return answer;
    }
    
    /**
     * 获取Java Client 测试类
     * @author 徐明龙 XuMingLong 
     * @return Java Client 测试类
     */
    protected TestsClass getJavaClientTests() {
        progressCallback.startTask(String.format("准备生成表%s的Mapper测试代码", introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
        }
        
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(calculateJavaClientTestsClassName());
        TestsClass testsClass = new TestsClass(type);
        testsClass.setVisibility(JavaVisibility.PUBLIC);
        mixedContext.setTestsClass(testsClass);
        //增加注释
        commentGenerator.addJavaFileComment(testsClass);
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addMapperTestsClassComment(testsClass, introspectedTable);
        }
        
        //增加引入
        testsClass.addStaticImport("org.assertj.core.api.Assertions.assertThat");
        testsClass.addImportedType("java.util.List");
        
        testsClass.addImportedType("org.junit.runner.RunWith");
        testsClass.addImportedType("org.springframework.beans.factory.annotation.Autowired");
        testsClass.addImportedType("org.springframework.boot.test.context.SpringBootTest");
        testsClass.addImportedType("org.springframework.test.context.junit4.SpringRunner");
        testsClass.addImportedType("org.springframework.test.context.web.WebAppConfiguration");
        testsClass.addImportedType("org.springframework.transaction.annotation.Transactional");
        testsClass.addImportedType("lombok.extern.log4j.Log4j2");
        
        String testSpringBootMainClass = calculateSpringBootTestClass();
        FullyQualifiedJavaType mainClass = new FullyQualifiedJavaType(testSpringBootMainClass); 
        testsClass.addImportedType(testSpringBootMainClass);
        
        FullyQualifiedJavaType mapperClassType = mixedContext.getMapper().getType();
        testsClass.addImportedType(mapperClassType);
        
        FullyQualifiedJavaType recordClassType = mixedContext.getBaseRecord().getType();
        testsClass.addImportedType(recordClassType);
        
        //增加注解
        testsClass.addAnnotation("@RunWith(SpringRunner.class)");
        testsClass.addAnnotation(String.join("", "@SpringBootTest(classes = ",mainClass.getShortName(),".class)"));
        testsClass.addAnnotation("@WebAppConfiguration");
        testsClass.addAnnotation("@Transactional");
        testsClass.addAnnotation("@Log4j2");
        
        //增加Mapper的引入
        Field field = new Field(StringUtils.uncapitalize(mapperClassType.getShortName()),mapperClassType);
        field.addAnnotation("@Autowired");
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addAutowiredMapperFieldComment(field, introspectedTable);
        }
        field.setVisibility(JavaVisibility.PRIVATE);
        testsClass.addField(field);
        
        //增加测试方法
        addInsertTestMethod();
        
        return testsClass;
    }
    
    
    
    /**
     * 增加测试Insert方法
     * @author 徐明龙 XuMingLong 
     */
    protected void addInsertTestMethod() {
        AbstractMethodGenerator methodGenerator = new TestsInsertMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }
    
    /**
     * 初始化并执行生成器
     * @author 徐明龙 XuMingLong 
     * @param methodGenerator 方法生成器
     */
    protected void initializeAndExecuteGenerator(AbstractMethodGenerator methodGenerator) {
        methodGenerator.setContext(context);
        methodGenerator.setIntrospectedTable(introspectedTable);
        methodGenerator.setProgressCallback(progressCallback);
        methodGenerator.setWarnings(warnings);
        methodGenerator.addMethod();
    }

    
    /**
     * 计算Java Client 测试类名称
     * @author 徐明龙 XuMingLong 
     * @return Java Client 测试类名称
     */
    protected String calculateJavaClientTestsClassName() {
        StringBuilder sb = new StringBuilder();
        sb.append(calculateJavaClientTestsPackage());
        sb.append('.');
        sb.append(this.mixedContext.getMapper().getType().getShortName());
        sb.append("Tests"); 
        return sb.toString();
    }
    
    /**
     * 计算Java Client测试类的Package
     * @author 徐明龙 XuMingLong 
     * @return Java Client测试类的Package
     */
    protected String calculateJavaClientTestsPackage() {
        return this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.EXTRA_TEST_CLIENT_TARGET_PACKAGE_KEY);
        
    }
  
    
    /**
     * 计算Spring Boot Test启动类
     * @author 徐明龙 XuMingLong 
     * @return Spring Boot Test启动类
     */
    protected String calculateSpringBootTestClass() {
        return this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.EXTRA_TEST_SPRING_BOOT_MAIN_CLASS_KEY);
    }

    /**
     * 设置生成的目标项目目录
     * @author 徐明龙 XuMingLong 
     */
    @Override
    public void setTargetProject() {
        this.targetProject =calculateTestProject();
    }

}
