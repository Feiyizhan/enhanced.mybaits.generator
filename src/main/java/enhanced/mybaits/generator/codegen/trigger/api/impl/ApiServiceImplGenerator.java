
package enhanced.mybaits.generator.codegen.trigger.api.impl;

import enhanced.mybaits.generator.CodeGeneratorUtils;
import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstractMethodGenerator;
import enhanced.mybaits.generator.codegen.AbstratEnhanceJavaGenerator;
import enhanced.mybaits.generator.codegen.IEnhanceCommentGenerator;
import enhanced.mybaits.generator.dom.java.ApiServiceImplClass;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * Api Service 接口实现类生成器
 * @author 徐明龙 XuMingLong 
 */
public class ApiServiceImplGenerator extends AbstratEnhanceJavaGenerator {


    public ApiServiceImplGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 设置Api Service接口实现类生成位置
     * @author 徐明龙 XuMingLong 
     */
    @Override
    public void setTargetProject() {
        this.targetProject = calculateJavaClientProject();
    }

    /**
     * 生成Api Service接口实现类代码
     * @author 徐明龙 XuMingLong 
     * @return ApiService的实现类
     */
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = new ArrayList<>();
        //生成Api Service 接口实现类
        ApiServiceImplClass apiServiceImplClass = getApiServiceImplClass();
        answer.add(apiServiceImplClass);
        return answer;
    }

    /**
     * 生成ApiService接口实现类代码
     * @author 徐明龙 XuMingLong 
     * @return ApiService的实现类
     */
    protected ApiServiceImplClass getApiServiceImplClass() {
        progressCallback.startTask(String.format("准备生成表%s的ApiService接口实现类", introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
        }
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(calculateApiServiceImplClassName());
        ApiServiceImplClass apiServiceImplClass = new ApiServiceImplClass(type);
        apiServiceImplClass.setVisibility(JavaVisibility.PUBLIC);
        mixedContext.setApiServiceImplClass(apiServiceImplClass);
        
        //增加注释
        commentGenerator.addJavaFileComment(apiServiceImplClass);
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addApiServiceImplClassComment(apiServiceImplClass, introspectedTable);
        }
        //增加Spring Service注解
        addSpringServiceAnnotation(apiServiceImplClass);
        //增加对应接口的引入
        addInterfaceImport(apiServiceImplClass);

        
        FullyQualifiedJavaType apiServiceInterfaceType = mixedContext.getApiServiceInterface().getType();
        apiServiceImplClass.addSuperInterface(apiServiceInterfaceType);
        FullyQualifiedJavaType applicationInterfaceType = mixedContext.getApplicationInterface().getType();
        
        //增加Application的注入
        Field field = new Field(CodeGeneratorUtils.getInterfaceVarName(mixedContext.getApplicationInterface()),applicationInterfaceType);
        field.addAnnotation("@Resource");
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addAutowiredMapperFieldComment(field, introspectedTable);
        }
        field.setVisibility(JavaVisibility.PRIVATE);
        apiServiceImplClass.addField(field);
        apiServiceImplClass.addImportedType("javax.annotation.Resource");
       
        //生成方法
        addInsertMethod();
        addGetByPrimaryKeyMethod();
        addUpdateByPrimaryKeyMethod();

        //增加默认的引入
        addDefaultImport(apiServiceImplClass);
        return apiServiceImplClass;
    }
    
    /**
     * 增加更新主键对应的记录的方法
     * @author 徐明龙 XuMingLong
     */
    protected void addUpdateByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new ApiServiceImplUpdateByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }

    /**
     * 增加获取主键对应的记录的方法
     * @author 徐明龙 XuMingLong
     */
    protected void addGetByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new ApiServiceImplGetByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }


    /**
     * 增加新增方法
     * @author 徐明龙 XuMingLong
     */
    protected void addInsertMethod() {
        AbstractMethodGenerator methodGenerator = new ApiServiceImplInsertMethodGenerator(mixedContext);
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
     * @param domainImplClass ApiService的实现类
     */
    protected void addDefaultImport(ApiServiceImplClass domainImplClass) {
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
        domainImplClass.addImportedType(this.mixedContext.getDTOClass().getType());
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
     * @param domainImplClass ApiService的实现类
     */
    protected void addInterfaceImport(ApiServiceImplClass domainImplClass) {
        domainImplClass.addImportedTypes(this.mixedContext.getApiServiceInterface().getImportedTypes());
    }
    

    /**
     * 计算ApiService接口实现类名称
     * @author 徐明龙 XuMingLong 
     * @return 计算ApiService接口实现类名称
     */
    protected String calculateApiServiceImplClassName() {
        StringBuilder sb = new StringBuilder();
        sb.append(calculateApiServiceImplClassPackage());
        sb.append('.');
        sb.append(this.mixedContext.getBaseRecord().getType().getShortName());
        sb.append("ApiServiceImpl"); 
        return sb.toString();
    }
    
    /**
     * 计算Api Service接口实现类的Package
     * @author 徐明龙 XuMingLong 
     * @return 计算ApiService接口实现类的Package
     */
    protected String calculateApiServiceImplClassPackage() {
        String testClientTargetPackage = this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.EXTRA_API_SERVICE_IMPL_TARGET_PACKAGE_KEY);
        if(!stringHasValue(testClientTargetPackage)) {
            return null;
        }else {
            return testClientTargetPackage;
        }
    }

}
