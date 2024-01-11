
package enhanced.mybaits.generator.repository;

import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstractMethodGenerator;
import enhanced.mybaits.generator.codegen.AbstratEnhanceJavaGenerator;
import enhanced.mybaits.generator.codegen.IEnhanceCommentGenerator;
import enhanced.mybaits.generator.codegen.service.*;
import enhanced.mybaits.generator.dom.java.RepositoryInterface;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * Repository 接口生成器
 * @author 徐明龙 XuMingLong 
 */
public class RepositoryInterfaceGenerator extends AbstratEnhanceJavaGenerator {

    public RepositoryInterfaceGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 生成Repository
     * @author 徐明龙 XuMingLong 
     * @return Repository接口类
     */
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        //生成Repository 接口类
        RepositoryInterface repositoryInterface = getRepositoryInterface();
        answer.add(repositoryInterface);
        return answer;
    }
    
    

    /**
     * 生成Repository 接口类
     * @author 徐明龙 XuMingLong 
     * @return Repository接口类
     */
    protected RepositoryInterface getRepositoryInterface() {
        progressCallback.startTask(String.format("准备生成表%s的Repository接口", introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
        }
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(calculateRepositoryInterfaceName());
        RepositoryInterface repositoryInterface = new RepositoryInterface(type);
        repositoryInterface.setVisibility(JavaVisibility.PUBLIC);
        mixedContext.setRepositoryInterface(repositoryInterface);
        //增加注释
        commentGenerator.addJavaFileComment(repositoryInterface);
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addRepositoryInterfaceComment(repositoryInterface, introspectedTable);
        }
        addInsertMethod();
        addGetByPrimaryKeyMethod();
        addGetAndLockByPrimaryKeyMethod();
        addUpdateByPrimaryKeyMethod();
//        addDeleteByPrimaryKeyMethod();
        
        //增加默认引入
        addDefaultImport(repositoryInterface);
        return repositoryInterface;
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
        AbstractMethodGenerator methodGenerator = new RepositoryInterfaceUpdateByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }
    /**
     * 增加获取主键对应的记录方法
     * @author 徐明龙 XuMingLong 
     */
    protected void addGetByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new RepositoryInterfaceGetByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }
    
    /**
     * 增加获取主键对应的记录的数据对象方法
     * @author 徐明龙 XuMingLong 
     */
    protected void addGetAndLockByPrimaryKeyMethod() {
        AbstractMethodGenerator methodGenerator = new RepositoryInterfaceGetAndLockByPrimaryKeyMethodGenerator(mixedContext);
        initializeAndExecuteGenerator(methodGenerator);
    }
    
    /**
     * 增加新增方法
     * @author 徐明龙 XuMingLong 
     */
    protected void addInsertMethod() {
        AbstractMethodGenerator methodGenerator = new RepositoryInterfaceInsertMethodGenerator(mixedContext);
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
     * @param repositoryInterface Repository接口类
     */
    protected void addDefaultImport(RepositoryInterface repositoryInterface) {
        repositoryInterface.getMethods().forEach((r)->{
            r.getParameters().forEach((p)->{
                repositoryInterface.addImportedType(p.getType());
            });
            if(r.getReturnType()!=null){
                repositoryInterface.addImportedType(r.getReturnType());
            }
            r.getTypeParameters().forEach((tp)->{
                tp.getExtendsTypes().forEach((et)->{
                    repositoryInterface.addImportedType(et);
                });
            });
        });

        repositoryInterface.getFields().forEach((r)->{
            repositoryInterface.addImportedType(r.getType());
        });

        repositoryInterface.addImportedTypes(repositoryInterface.getSuperInterfaceTypes());
        
    }
    
    /**
     * 计算Repository接口名称
     * @author 徐明龙 XuMingLong 
     * @return 计算Repository接口名称
     */
    protected String calculateRepositoryInterfaceName() {
        StringBuilder sb = new StringBuilder();
        sb.append(calculateServiceInterfacePackage());
        sb.append('.');
        sb.append('I');
        sb.append(this.mixedContext.getBaseRecord().getType().getShortName());
        sb.append("Repository");
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
            .getProperty(EnhanceConstant.EXTRA_REPOSITORY_TARGET_PACKAGE_KEY);
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
