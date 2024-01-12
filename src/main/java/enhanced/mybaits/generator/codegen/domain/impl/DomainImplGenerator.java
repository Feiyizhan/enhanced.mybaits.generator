
package enhanced.mybaits.generator.codegen.domain.impl;

import enhanced.mybaits.generator.CodeGeneratorUtils;
import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstractMethodGenerator;
import enhanced.mybaits.generator.codegen.AbstratEnhanceJavaGenerator;
import enhanced.mybaits.generator.codegen.IEnhanceCommentGenerator;
import enhanced.mybaits.generator.dom.java.DomainImplClass;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * Domain 接口实现类生成器
 * @author 徐明龙 XuMingLong 
 */
public class DomainImplGenerator extends AbstratEnhanceJavaGenerator {


    public DomainImplGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 设置Domain接口实现类生成位置
     * @author 徐明龙 XuMingLong 
     */
    @Override
    public void setTargetProject() {
        this.targetProject = calculateJavaClientProject();
    }

    /**
     * 生成Domain接口实现类代码
     * @author 徐明龙 XuMingLong 
     * @return Domain的实现类
     */
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = new ArrayList<>();
        //生成Domain 接口实现类
        DomainImplClass domainImplClass = getDomainImplClass();
        answer.add(domainImplClass);
        return answer;
    }

    /**
     * 生成Domain接口实现类代码
     * @author 徐明龙 XuMingLong 
     * @return Domain的实现类
     */
    protected DomainImplClass getDomainImplClass() {
        progressCallback.startTask(String.format("准备生成表%s的Domain接口实现类", introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
        }
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(calculateDomainImplClassName());
        DomainImplClass domainImplClass = new DomainImplClass(type);
        domainImplClass.setVisibility(JavaVisibility.PUBLIC);
        mixedContext.setDomainImplClass(domainImplClass);
        
        //增加注释
        commentGenerator.addJavaFileComment(domainImplClass);
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addDomainImplClassComment(domainImplClass, introspectedTable);
        }
        //增加Spring Service注解
        addSpringServiceAnnotation(domainImplClass);
        //增加对应接口的引入
        addInterfaceImport(domainImplClass);

        
        FullyQualifiedJavaType domainInterfaceType = mixedContext.getDomainInterface().getType();
        domainImplClass.addSuperInterface(domainInterfaceType);
        FullyQualifiedJavaType repositoryClassType = mixedContext.getRepositoryInterface().getType();
        
        //增加Repository的注入
        Field field = new Field(CodeGeneratorUtils.getInterfaceVarName(mixedContext.getRepositoryInterface()),repositoryClassType);
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
//        addDeleteByPrimaryKeyMethod();
        
        //增加默认的引入
        addDefaultImport(domainImplClass);
        return domainImplClass;
    }
    
//    /**
//     * 增加删除主键对应的记录的方法
//     * @author 徐明龙 XuMingLong
//     */
//    protected void addDeleteByPrimaryKeyMethod() {
//        AbstractMethodGenerator methodGenerator = new DomainImplDeleteByPrimaryKeyMethodGenerator(mixedContext);
//        initializeAndExecuteGenerator(methodGenerator);
//    }

    /**
     * 增加更新主键对应的记录的方法
     * @author 徐明龙 XuMingLong
     */
    protected void addUpdateByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new DomainImplUpdateByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }

    /**
     * 增加获取主键对应的记录的方法
     * @author 徐明龙 XuMingLong
     */
    protected void addGetByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new DomainImplGetByPrimaryKeyMethodGenerator(mixedContext);
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
        AbstractMethodGenerator methodGenerator = new DomainImplInsertMethodGenerator(mixedContext);
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
     * @param domainImplClass Domain的实现类
     */
    protected void addDefaultImport(DomainImplClass domainImplClass) {
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
     * @param domainImplClass Domain的实现类
     */
    protected void addInterfaceImport(DomainImplClass domainImplClass) {
        domainImplClass.addImportedTypes(this.mixedContext.getDomainInterface().getImportedTypes());
    }
    

    /**
     * 计算Domain接口实现类名称
     * @author 徐明龙 XuMingLong 
     * @return 计算Domain接口实现类名称
     */
    protected String calculateDomainImplClassName() {
        StringBuilder sb = new StringBuilder();
        sb.append(calculateDomainImplClassPackage());
        sb.append('.');
        sb.append(this.mixedContext.getBaseRecord().getType().getShortName());
        sb.append("DomainImpl"); 
        return sb.toString();
    }
    
    /**
     * 计算Domain接口实现类的Package
     * @author 徐明龙 XuMingLong 
     * @return 计算Domain接口实现类的Package
     */
    protected String calculateDomainImplClassPackage() {
        String testClientTargetPackage = this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.EXTRA_DOMAIN_IMPL_TARGET_PACKAGE_KEY);
        if(!stringHasValue(testClientTargetPackage)) {
            return null;
        }else {
            return testClientTargetPackage;
        }
    }

}
