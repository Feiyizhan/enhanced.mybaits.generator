
package enhanced.mybaits.generator.repository.impl;

import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstractMethodGenerator;
import enhanced.mybaits.generator.codegen.AbstratEnhanceJavaGenerator;
import enhanced.mybaits.generator.codegen.IEnhanceCommentGenerator;
import enhanced.mybaits.generator.codegen.service.impl.*;
import enhanced.mybaits.generator.dom.java.RepositoryImplClass;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * Repository 接口实现类生成器
 * @author 徐明龙 XuMingLong 
 */
public class RepositoryImplGenerator extends AbstratEnhanceJavaGenerator {


    public RepositoryImplGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 设置Repository接口实现类生成位置
     * @author 徐明龙 XuMingLong 
     */
    @Override
    public void setTargetProject() {
        this.targetProject = calculateJavaClientProject();
    }

    /**
     * 生成Repository接口实现类代码
     * @author 徐明龙 XuMingLong 
     * @return Repository的实现类
     */
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = new ArrayList<>();
        //生成Repository 接口实现类
        RepositoryImplClass repositoryImplClass = getRepositoryImplClass();
        answer.add(repositoryImplClass);
        return answer;
    }

    /**
     * 生成Repository接口实现类代码
     * @author 徐明龙 XuMingLong 
     * @return Repository的实现类
     */
    protected RepositoryImplClass getRepositoryImplClass() {
        progressCallback.startTask(String.format("准备生成表%s的Repository接口实现类", introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
        }
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(calculateRepositoryImplClassName());
        RepositoryImplClass repositoryImplClass = new RepositoryImplClass(type);
        repositoryImplClass.setVisibility(JavaVisibility.PUBLIC);
        mixedContext.setRepositoryImplClass(repositoryImplClass);
        
        //增加注释
        commentGenerator.addJavaFileComment(repositoryImplClass);
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addRepositoryImplClassComment(repositoryImplClass, introspectedTable);
        }
        //增加Spring Repository注解
        addSpringRepositoryAnnotation(repositoryImplClass);
        //增加对应接口的引入
        addInterfaceImport(repositoryImplClass);

        
        FullyQualifiedJavaType repositoryInterfaceType = mixedContext.getRepositoryInterface().getType();
        repositoryImplClass.addSuperInterface(repositoryInterfaceType);
        FullyQualifiedJavaType mapperClassType = mixedContext.getMapper().getType();
        
        //增加Mapper的注入
        Field field = new Field(StringUtils.uncapitalize(mapperClassType.getShortName()),mapperClassType);
        field.addAnnotation("@Resource");
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addAutowiredMapperFieldComment(field, introspectedTable);
        }
        field.setVisibility(JavaVisibility.PRIVATE);
        repositoryImplClass.addField(field);
        repositoryImplClass.addImportedType("javax.annotation.Resource");
       
        //生成方法
        addInsertMethod();
        addGetByPrimaryKeyMethod();
        addGetAndLockByPrimaryKeyMethod();
        addUpdateByPrimaryKeyMethod();
//        addDeleteByPrimaryKeyMethod();
        
        //增加默认的引入
        addDefaultImport(repositoryImplClass);
        return repositoryImplClass;
    }
    
    /**
     * 增加删除主键对应的记录的方法
     * @author 徐明龙 XuMingLong 
     */
    protected void addDeleteByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new ServiceImplDeleteByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }
    
    /**
     * 增加更新主键对应的记录的方法
     * @author 徐明龙 XuMingLong 
     */
    protected void addUpdateByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new RepositoryImplUpdateByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }
    
    /**
     * 增加获取主键对应的记录的方法
     * @author 徐明龙 XuMingLong 
     */
    protected void addGetByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new RepositoryImplGetByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }
    
    /**
     * 增加获取并锁定主键对应的记录的返回结果对象方法
     * @author 徐明龙 XuMingLong 
     */
    protected void addGetAndLockByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new RepositoryImplGetAndLockByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }
    
    /**
     * 增加新增方法
     * @author 徐明龙 XuMingLong 
     */
    protected void addInsertMethod() {
        AbstractMethodGenerator methodGenerator = new RepositoryImplInsertMethodGenerator(mixedContext);
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
     * @param repositoryImplClass Repository的实现类
     */
    protected void addDefaultImport(RepositoryImplClass repositoryImplClass) {
        repositoryImplClass.getMethods().forEach((r)->{
            r.getParameters().forEach((p)->{
                repositoryImplClass.addImportedType(p.getType());
            });
            if(r.getReturnType()!=null){
                repositoryImplClass.addImportedType(r.getReturnType());
            }
            r.getTypeParameters().forEach((tp)->{
                tp.getExtendsTypes().forEach((et)->{
                    repositoryImplClass.addImportedType(et);
                });
            });
        });

        repositoryImplClass.getFields().forEach((r)->{
            repositoryImplClass.addImportedType(r.getType());
        });

        repositoryImplClass.addImportedType(this.mixedContext.getBaseRecord().getType());
        repositoryImplClass.addImportedTypes(repositoryImplClass.getSuperInterfaceTypes());

        repositoryImplClass.addImportedType("org.apache.commons.collections4.CollectionUtils");
        repositoryImplClass.addStaticImport(getNowUtilsName());
        repositoryImplClass.addImportedType(getNewLocalDateTimeType());
        repositoryImplClass.addImportedType("org.springframework.beans.BeanUtils");
        repositoryImplClass.addImportedType(FullyQualifiedJavaType.getNewArrayListInstance());
        
    }
    
    
    /**
     * 增加对应的接口的引入
     * @author 徐明龙 XuMingLong 
     * @param repositoryImplClass Repository的实现类
     */
    protected void addInterfaceImport(RepositoryImplClass repositoryImplClass) {
        repositoryImplClass.addImportedTypes(this.mixedContext.getRepositoryInterface().getImportedTypes());
    }
    
    

    
    /**
     * 计算Repository接口实现类名称
     * @author 徐明龙 XuMingLong 
     * @return 计算Repository接口实现类名称
     */
    protected String calculateRepositoryImplClassName() {
        StringBuilder sb = new StringBuilder();
        sb.append(calculateRepositoryImplClassPackage());
        sb.append('.');
        sb.append(this.mixedContext.getBaseRecord().getType().getShortName());
        sb.append("RepositoryImpl");
        return sb.toString();
    }
    
    /**
     * 计算Repository接口实现类的Package
     * @author 徐明龙 XuMingLong 
     * @return 计算Repository接口实现类的Package
     */
    protected String calculateRepositoryImplClassPackage() {
        String testClientTargetPackage = this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.EXTRA_REPOSITORY_IMPL_TARGET_PACKAGE_KEY);
        if(!stringHasValue(testClientTargetPackage)) {
            return null;
        }else {
            return testClientTargetPackage;
        }
    }

}
