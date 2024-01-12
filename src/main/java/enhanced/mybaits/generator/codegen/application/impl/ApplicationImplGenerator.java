
package enhanced.mybaits.generator.codegen.application.impl;

import enhanced.mybaits.generator.CodeGeneratorUtils;
import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstractMethodGenerator;
import enhanced.mybaits.generator.codegen.AbstratEnhanceJavaGenerator;
import enhanced.mybaits.generator.codegen.IEnhanceCommentGenerator;
import enhanced.mybaits.generator.codegen.domain.impl.DamainImplGetAndLockByPrimaryKeyMethodGenerator;
import enhanced.mybaits.generator.dom.java.ApplicationImplClass;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * Application 接口实现类生成器
 * @author 徐明龙 XuMingLong 
 */
public class ApplicationImplGenerator extends AbstratEnhanceJavaGenerator {


    public ApplicationImplGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 设置Application接口实现类生成位置
     * @author 徐明龙 XuMingLong 
     */
    @Override
    public void setTargetProject() {
        this.targetProject = calculateJavaClientProject();
    }

    /**
     * 生成Application接口实现类代码
     * @author 徐明龙 XuMingLong 
     * @return Application的实现类
     */
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = new ArrayList<>();
        //生成Application 接口实现类
        ApplicationImplClass domainImplClass = getApplicationImplClass();
        answer.add(domainImplClass);
        return answer;
    }

    /**
     * 生成Application接口实现类代码
     * @author 徐明龙 XuMingLong 
     * @return Application的实现类
     */
    protected ApplicationImplClass getApplicationImplClass() {
        progressCallback.startTask(String.format("准备生成表%s的Application接口实现类", introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
        }
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(calculateApplicationImplClassName());
        ApplicationImplClass domainImplClass = new ApplicationImplClass(type);
        domainImplClass.setVisibility(JavaVisibility.PUBLIC);
        mixedContext.setApplicationImplClass(domainImplClass);
        
        //增加注释
        commentGenerator.addJavaFileComment(domainImplClass);
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addApplicationImplClassComment(domainImplClass, introspectedTable);
        }
        //增加Spring Service注解
        addSpringServiceAnnotation(domainImplClass);
        //增加对应接口的引入
        addInterfaceImport(domainImplClass);

        
        FullyQualifiedJavaType applicationInterfaceType = mixedContext.getApplicationInterface().getType();
        domainImplClass.addSuperInterface(applicationInterfaceType);
        FullyQualifiedJavaType domainInterfaceType = mixedContext.getDomainInterface().getType();
        
        //增加Repository的注入
        Field field = new Field(CodeGeneratorUtils.getInterfaceVarName(mixedContext.getDomainInterface()),domainInterfaceType);
        field.addAnnotation("@Resource");
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addAutowiredMapperFieldComment(field, introspectedTable);
        }
        field.setVisibility(JavaVisibility.PRIVATE);
        domainImplClass.addField(field);
        domainImplClass.addImportedType("javax.annotation.Resource");
       
        //生成方法
        addInsertMethod();
        addGetByPrimaryKeyMethod();
        addGetAndLockByPrimaryKeyMethod();
        addUpdateByPrimaryKeyMethod();

        //增加默认的引入
        addDefaultImport(domainImplClass);
        return domainImplClass;
    }
    
    /**
     * 增加更新主键对应的记录的方法
     * @author 徐明龙 XuMingLong
     */
    protected void addUpdateByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new ApplicationImplUpdateByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }

    /**
     * 增加获取主键对应的记录的方法
     * @author 徐明龙 XuMingLong
     */
    protected void addGetByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new ApplicationImplGetByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }

    /**
     * 增加获取并锁定主键对应的记录的方法
     * @author 徐明龙 XuMingLong
     */
    protected void addGetAndLockByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new DamainImplGetAndLockByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }

    /**
     * 增加新增方法
     * @author 徐明龙 XuMingLong
     */
    protected void addInsertMethod() {
        AbstractMethodGenerator methodGenerator = new ApplicationImplInsertMethodGenerator(mixedContext);
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
     * 增加默认的引入
     * @author 徐明龙 XuMingLong 
     * @param domainImplClass Application的实现类
     */
    protected void addDefaultImport(ApplicationImplClass domainImplClass) {
        domainImplClass.getMethods().forEach((r)->{
            r.getParameters().forEach((p)->{
                domainImplClass.addImportedType(p.getType());
            });
            domainImplClass.addImportedType(r.getReturnType());
            r.getTypeParameters().forEach((tp)->{
                tp.getExtendsTypes().forEach((et)->{
                    domainImplClass.addImportedType(et);
                });
            });
        });
        
        domainImplClass.getFields().forEach((r)->{
            domainImplClass.addImportedType(r.getType());
        });
        domainImplClass.addImportedType(this.mixedContext.getDOClass().getType());
        domainImplClass.addImportedTypes(domainImplClass.getSuperInterfaceTypes());
        
        domainImplClass.addImportedType("org.apache.commons.collections4.CollectionUtils");
        domainImplClass.addStaticImport(getNowUtilsName());
        domainImplClass.addImportedType(getNewLocalDateTimeType());
        domainImplClass.addImportedType("org.springframework.beans.BeanUtils");
        domainImplClass.addImportedType(FullyQualifiedJavaType.getNewArrayListInstance());
        
    }
    
    
    /**
     * 增加对应的接口的引入
     * @author 徐明龙 XuMingLong 
     * @param domainImplClass Application的实现类
     */
    protected void addInterfaceImport(ApplicationImplClass domainImplClass) {
        domainImplClass.addImportedTypes(this.mixedContext.getApplicationInterface().getImportedTypes());
    }
    

    /**
     * 计算Application接口实现类名称
     * @author 徐明龙 XuMingLong 
     * @return 计算Application接口实现类名称
     */
    protected String calculateApplicationImplClassName() {
        StringBuilder sb = new StringBuilder();
        sb.append(calculateApplicationImplClassPackage());
        sb.append('.');
        sb.append(this.mixedContext.getBaseRecord().getType().getShortName());
        sb.append("ApplicationImpl"); 
        return sb.toString();
    }
    
    /**
     * 计算Application接口实现类的Package
     * @author 徐明龙 XuMingLong 
     * @return 计算Application接口实现类的Package
     */
    protected String calculateApplicationImplClassPackage() {
        String testClientTargetPackage = this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.EXTRA_APPLICATION_IMPL_TARGET_PACKAGE_KEY);
        if(!stringHasValue(testClientTargetPackage)) {
            return null;
        }else {
            return testClientTargetPackage;
        }
    }

}
