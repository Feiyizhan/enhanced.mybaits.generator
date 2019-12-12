
package enhanced.mybaits.generator.codegen.service;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;

import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstractMethodGenerator;
import enhanced.mybaits.generator.codegen.AbstratEnhanceJavaGenerator;
import enhanced.mybaits.generator.codegen.IEnhanceCommentGenerator;
import enhanced.mybaits.generator.dom.java.ServiceInterface;

/**
 * Service生成器
 * @author 徐明龙 XuMingLong 
 */
public class ServiceInterfaceGenerator extends AbstratEnhanceJavaGenerator {

    public ServiceInterfaceGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 生成Service
     * @author 徐明龙 XuMingLong 
     * @return Service接口类
     */
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        //生成Service 接口类
        ServiceInterface serviceInterface = getServiceInterface();
        answer.add(serviceInterface);
        return answer;
    }
    
    

    /**
     * 生成Service 接口类
     * @author 徐明龙 XuMingLong 
     * @return Service接口类
     */
    protected ServiceInterface getServiceInterface() {
        progressCallback.startTask(String.format("准备生成表%s的Service接口", introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
        }
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(calculateServiceInterfaceName());
        ServiceInterface serviceInterface = new ServiceInterface(type);
        serviceInterface.setVisibility(JavaVisibility.PUBLIC);
        mixedContext.setServiceInterface(serviceInterface);
        //增加注释
        commentGenerator.addJavaFileComment(serviceInterface);
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addServiceInterfaceComment(serviceInterface, introspectedTable);
        }
        addInsertMethod();
        addGetByPrimaryKeyMethod();
        addGetResultByPrimaryKeyMethod();
        addUpdateByPrimaryKeyMethod();
        addDeleteByPrimaryKeyMethod();
        
        //增加默认引入
        addDefaultImport(serviceInterface);
        return serviceInterface;
    }
    /**
     * 增加删除指定记录方法
     * @author 徐明龙 XuMingLong 
     */
    protected void addDeleteByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new ServiceInterFaceDeleteByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }
    
    /**
     * 增加更新指定记录方法
     * @author 徐明龙 XuMingLong 
     */
    protected void addUpdateByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new ServiceInterFaceUpdateByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }
    /**
     * 增加获取主键对应的记录方法
     * @author 徐明龙 XuMingLong 
     */
    protected void addGetByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new ServiceInterFaceGetByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }
    
    /**
     * 增加获取主键对应的记录的返回结果对象方法
     * @author 徐明龙 XuMingLong 
     */
    protected void addGetResultByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new ServiceInterFaceGetResultByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }
    
    /**
     * 增加新增方法
     * @author 徐明龙 XuMingLong 
     */
    protected void addInsertMethod() {
        AbstractMethodGenerator methodGenerator = new ServiceInterFaceInsertMethodGenerator(mixedContext);
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
     * @param serviceInterface Service接口类
     */
    protected void addDefaultImport(ServiceInterface serviceInterface) {
        serviceInterface.getMethods().forEach((r)->{
            r.getParameters().forEach((p)->{
                serviceInterface.addImportedType(p.getType());
            });
            serviceInterface.addImportedType(r.getReturnType());
            r.getTypeParameters().forEach((tp)->{
                tp.getExtendsTypes().forEach((et)->{
                    serviceInterface.addImportedType(et);
                });
            });
        });
        
        serviceInterface.getFields().forEach((r)->{
            serviceInterface.addImportedType(r.getType());
        });
        
        serviceInterface.addImportedTypes(serviceInterface.getSuperInterfaceTypes());
        
    }
    
    /**
     * 计算Service接口名称
     * @author 徐明龙 XuMingLong 
     * @return 计算Service接口名称
     */
    protected String calculateServiceInterfaceName() {
        StringBuilder sb = new StringBuilder();
        sb.append(calculateServiceInterfacePackage());
        sb.append('.');
        sb.append('I');
        sb.append(this.mixedContext.getBaseRecord().getType().getShortName());
        sb.append("Service"); 
        return sb.toString();
    }
    
    /**
     * 计算Service接口的Package
     * @author 徐明龙 XuMingLong 
     * @return 计算Service接口的Package
     */
    protected String calculateServiceInterfacePackage() {
        String value = this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.EXTRA_SERVICE_TARGET_PACKAGE_KEY);
        if(!stringHasValue(value)) {
            return null;
        }else {
            return value;
        }
    }

    /**
     * 设置Service生成位置
     * @author 徐明龙 XuMingLong 
     */
    @Override
    public void setTargetProject() {
        this.targetProject = calculateJavaClientProject();
    }

}
