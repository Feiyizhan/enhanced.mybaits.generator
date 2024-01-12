
package enhanced.mybaits.generator.codegen.application;

import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstractMethodGenerator;
import enhanced.mybaits.generator.codegen.AbstratEnhanceJavaGenerator;
import enhanced.mybaits.generator.codegen.IEnhanceCommentGenerator;
import enhanced.mybaits.generator.dom.java.ApplicationInterface;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * Application生成器
 * @author 徐明龙 XuMingLong 
 */
public class ApplicationInterfaceGenerator extends AbstratEnhanceJavaGenerator {

    public ApplicationInterfaceGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 生成Application
     * @author 徐明龙 XuMingLong 
     * @return Application接口类
     */
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = new ArrayList<>();
        //生成Application 接口类
        ApplicationInterface domainInterface = getApplicationInterface();
        answer.add(domainInterface);
        return answer;
    }
    
    

    /**
     * 生成Application 接口类
     * @author 徐明龙 XuMingLong 
     * @return Application接口类
     */
    protected ApplicationInterface getApplicationInterface() {
        progressCallback.startTask(String.format("准备生成表%s的Application接口", introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
        }
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(calculateApplicationInterfaceName());
        ApplicationInterface domainInterface = new ApplicationInterface(type);
        domainInterface.setVisibility(JavaVisibility.PUBLIC);
        mixedContext.setApplicationInterface(domainInterface);
        //增加注释
        commentGenerator.addJavaFileComment(domainInterface);
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addApplicationInterfaceComment(domainInterface, introspectedTable);
        }
        addInsertMethod();
        addGetByPrimaryKeyMethod();
        addUpdateByPrimaryKeyMethod();

        //增加默认引入
        addDefaultImport(domainInterface);
        return domainInterface;
    }

    /**
     * 增加更新指定记录方法
     * @author 徐明龙 XuMingLong
     */
    protected void addUpdateByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new ApplicationInterfaceUpdateByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }

    /**
     * 增加获取主键对应的记录方法
     * @author 徐明龙 XuMingLong
     */
    protected void addGetByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new ApplicationInterfaceGetByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }

    /**
     * 增加新增方法
     * @author 徐明龙 XuMingLong
     */
    protected void addInsertMethod() {
        AbstractMethodGenerator methodGenerator = new ApplicationInterfaceInsertMethodGenerator(mixedContext);
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
     * @param domainInterface Application接口类
     */
    protected void addDefaultImport(ApplicationInterface domainInterface) {
        domainInterface.getMethods().forEach((r)->{
            r.getParameters().forEach((p)->{
                domainInterface.addImportedType(p.getType());
            });
            if(r.getReturnType()!=null){
                domainInterface.addImportedType(r.getReturnType());
            }
            r.getTypeParameters().forEach((tp)->{
                tp.getExtendsTypes().forEach((et)->{
                    domainInterface.addImportedType(et);
                });
            });
        });
        
        domainInterface.getFields().forEach((r)->{
            domainInterface.addImportedType(r.getType());
        });
        
        domainInterface.addImportedTypes(domainInterface.getSuperInterfaceTypes());
        
    }
    
    /**
     * 计算Application接口名称
     * @author 徐明龙 XuMingLong 
     * @return 计算Application接口名称
     */
    protected String calculateApplicationInterfaceName() {
        StringBuilder sb = new StringBuilder();
        sb.append(calculateApplicationInterfacePackage());
        sb.append('.');
        sb.append('I');
        sb.append(this.mixedContext.getBaseRecord().getType().getShortName());
        sb.append("Application"); 
        return sb.toString();
    }
    
    /**
     * 计算Application接口的Package
     * @author 徐明龙 XuMingLong 
     * @return 计算Application接口的Package
     */
    protected String calculateApplicationInterfacePackage() {
        String value = this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.EXTRA_APPLICATION_TARGET_PACKAGE_KEY);
        if(!stringHasValue(value)) {
            return null;
        }else {
            return value;
        }
    }

    /**
     * 设置Application生成位置
     * @author 徐明龙 XuMingLong 
     */
    @Override
    public void setTargetProject() {
        this.targetProject = calculateJavaClientProject();
    }

}
