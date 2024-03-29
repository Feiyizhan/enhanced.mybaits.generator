
package enhanced.mybaits.generator.codegen.service.impl;

import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstractMethodGenerator;
import enhanced.mybaits.generator.codegen.AbstratEnhanceJavaGenerator;
import enhanced.mybaits.generator.codegen.IEnhanceCommentGenerator;
import enhanced.mybaits.generator.dom.java.ServiceImplClass;
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
 * Service 接口实现类生成器
 * @author 徐明龙 XuMingLong 
 */
public class ServiceImplGenerator extends AbstratEnhanceJavaGenerator {


    public ServiceImplGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 设置Service接口实现类生成位置
     * @author 徐明龙 XuMingLong 
     */
    @Override
    public void setTargetProject() {
        this.targetProject = calculateJavaClientProject();
    }

    /**
     * 生成Service接口实现类代码
     * @author 徐明龙 XuMingLong 
     * @return Service的实现类
     */
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        //生成Service 接口实现类
        ServiceImplClass serviceImplClass = getServiceImplClass();
        answer.add(serviceImplClass);
        return answer;
    }

    /**
     * 生成Service接口实现类代码
     * @author 徐明龙 XuMingLong 
     * @return Service的实现类
     */
    protected ServiceImplClass getServiceImplClass() {
        progressCallback.startTask(String.format("准备生成表%s的Service接口实现类", introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
        }
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(calculateServiceImplClassName());
        ServiceImplClass serviceImplClass = new ServiceImplClass(type);
        serviceImplClass.setVisibility(JavaVisibility.PUBLIC);
        mixedContext.setServiceImplClass(serviceImplClass);
        
        //增加注释
        commentGenerator.addJavaFileComment(serviceImplClass);
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addServiceImplClassComment(serviceImplClass, introspectedTable);
        }
        //增加Spring Service注解
        addSpringServiceAnnotation(serviceImplClass);
        //增加对应接口的引入
        addInterfaceImport(serviceImplClass);

        
        FullyQualifiedJavaType serviceInterfaceType = mixedContext.getServiceInterface().getType();
        serviceImplClass.addSuperInterface(serviceInterfaceType);
        FullyQualifiedJavaType mapperClassType = mixedContext.getMapper().getType();
        
        //增加Mapper的注入
        Field field = new Field(StringUtils.uncapitalize(mapperClassType.getShortName()),mapperClassType);
        field.addAnnotation("@Autowired");
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addAutowiredMapperFieldComment(field, introspectedTable);
        }
        field.setVisibility(JavaVisibility.PRIVATE);
        serviceImplClass.addField(field);
        serviceImplClass.addImportedType("org.springframework.beans.factory.annotation.Autowired");
       
        //生成方法
        addInsertMethod();
        addGetByPrimaryKeyMethod();
        addGetResultByPrimaryKeyMethod();
        addUpdateByPrimaryKeyMethod();
        addDeleteByPrimaryKeyMethod();
        
        //增加默认的引入
        addDefaultImport(serviceImplClass);
        return serviceImplClass;
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
        AbstractMethodGenerator methodGenerator = new ServiceImplUpdateByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }
    
    /**
     * 增加获取主键对应的记录的方法
     * @author 徐明龙 XuMingLong 
     */
    protected void addGetByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new ServiceImplGetByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }
    
    /**
     * 增加获取主键对应的记录的返回结果对象方法
     * @author 徐明龙 XuMingLong 
     */
    protected void addGetResultByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new ServiceImplGetResByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }
    
    /**
     * 增加新增方法
     * @author 徐明龙 XuMingLong 
     */
    protected void addInsertMethod() {
        AbstractMethodGenerator methodGenerator = new ServiceImplInsertMethodGenerator(mixedContext);
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
     * @param serviceImplClass Service的实现类
     */
    protected void addDefaultImport(ServiceImplClass serviceImplClass) {
        serviceImplClass.getMethods().forEach((r)->{
            r.getParameters().forEach((p)->{
                serviceImplClass.addImportedType(p.getType());
            });
            serviceImplClass.addImportedType(r.getReturnType());
            r.getTypeParameters().forEach((tp)->{
                tp.getExtendsTypes().forEach((et)->{
                    serviceImplClass.addImportedType(et);
                });
            });
        });
        
        serviceImplClass.getFields().forEach((r)->{
            serviceImplClass.addImportedType(r.getType());
        });
        
        serviceImplClass.addImportedTypes(serviceImplClass.getSuperInterfaceTypes());
        
        serviceImplClass.addImportedType("org.apache.commons.collections4.CollectionUtils");
        serviceImplClass.addStaticImport(getNowUtilsName());
        serviceImplClass.addImportedType(getNewLocalDateTimeType());
        serviceImplClass.addImportedType("org.springframework.beans.BeanUtils");
        serviceImplClass.addImportedType(FullyQualifiedJavaType.getNewArrayListInstance());
        
    }
    
    
    /**
     * 增加对应的接口的引入
     * @author 徐明龙 XuMingLong 
     * @param serviceImplClass Service的实现类
     */
    protected void addInterfaceImport(ServiceImplClass serviceImplClass) {
        serviceImplClass.addImportedTypes(this.mixedContext.getServiceInterface().getImportedTypes());
    }
    

    /**
     * 计算Service接口实现类名称
     * @author 徐明龙 XuMingLong 
     * @return 计算Service接口实现类名称
     */
    protected String calculateServiceImplClassName() {
        StringBuilder sb = new StringBuilder();
        sb.append(calculateServiceImplClassPackage());
        sb.append('.');
        sb.append(this.mixedContext.getBaseRecord().getType().getShortName());
        sb.append("ServiceImpl"); 
        return sb.toString();
    }
    
    /**
     * 计算Service接口实现类的Package
     * @author 徐明龙 XuMingLong 
     * @return 计算Service接口实现类的Package
     */
    protected String calculateServiceImplClassPackage() {
        String testClientTargetPackage = this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.EXTRA_SERVICE_IMPL_TARGET_PACKAGE_KEY);
        if(!stringHasValue(testClientTargetPackage)) {
            return null;
        }else {
            return testClientTargetPackage;
        }
    }

}
