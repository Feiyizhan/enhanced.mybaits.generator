
package enhanced.mybaits.generator.codegen.domain;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.IEnhanceCommentGenerator;
import enhanced.mybaits.generator.enums.DomainMethodEnum;
import enhanced.mybaits.generator.enums.DomainImplExtraMethodEnum;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain 接口新增方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class DomainInterfaceInsertMethodGenerator extends AbstractDomainInterfaceMethodGenerator {

    public DomainInterfaceInsertMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 增加额外的方法
     * @author 徐明龙 XuMingLong
     * @param method 待处理的方法
     * @return 额外的方法
     */
    @Override protected List<Method> addExtraMethod(Method method) {
        List<Method> methodList = new ArrayList<>();
        Method verifyForInsertMethod = new Method();
        verifyForInsertMethod.setName(DomainImplExtraMethodEnum.VERIFY_FOR_INSERT.getValue());

        //参数
        FullyQualifiedJavaType dTOType = this.mixedContext.getDTOClass().getType();
        Parameter dTOParameter = new Parameter(dTOType, dTOVarName);
        verifyForInsertMethod.addParameter(dTOParameter);
        this.mixedContext.getRepositoryInterface().addImportedType(dTOType);

        FullyQualifiedJavaType userType = new FullyQualifiedJavaType(getUserClassName());
        Parameter userParameter = new Parameter(userType, userVarName);
        verifyForInsertMethod.addParameter(userParameter);
        this.mixedContext.getRepositoryInterface().addImportedType(userType);

        verifyForInsertMethod.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = getListFormValidErrorType();
        //增加返回参数
        verifyForInsertMethod.setReturnType(returnType);
        returnType.getImportList().forEach((r)->{
            this.mixedContext.getDomainInterface().addImportedType(new FullyQualifiedJavaType(r));
        });

        //生成注释
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        CommentGenerator commentGenerator = context.getCommentGenerator();
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
            enhanceCommentGenerator.addDomainExtraMethodComment(verifyForInsertMethod);
        }
        methodList.add(verifyForInsertMethod);
        return methodList;
    }

    /**
     * 设置返回参数
     * @author 徐明龙 XuMingLong 
     * @param method 待处理的方法
     */
    @Override
    protected void addMethodParameter(Method method) {
        FullyQualifiedJavaType dTOType = this.mixedContext.getDTOClass().getType();
        Parameter dTOParameter = new Parameter(dTOType, dTOVarName);
        method.addParameter(dTOParameter);
        this.mixedContext.getRepositoryInterface().addImportedType(dTOType);

        FullyQualifiedJavaType userType = new FullyQualifiedJavaType(getUserClassName());
        Parameter userParameter = new Parameter(userType, userVarName);
        method.addParameter(userParameter);
        this.mixedContext.getRepositoryInterface().addImportedType(userType);

        FullyQualifiedJavaType nowType = new FullyQualifiedJavaType(getNowClassName());
        Parameter nowParameter = new Parameter(nowType, nowVarName);
        method.addParameter(nowParameter);
        this.mixedContext.getRepositoryInterface().addImportedType(nowType);
        
    }

    /**
     * 设置返回参数
     * @author 徐明龙 XuMingLong 
     * @param method 待处理的方法
     */
    @Override
    protected void setMethodReturnType(Method method) {
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(getIdClassName());
        method.setReturnType(returnType);
        this.mixedContext.getRepositoryInterface().addImportedType(returnType);
    }

    /**
     * 获取Domain方法对应的枚举
     * @author 徐明龙 XuMingLong 
     * @return 返回方法对应的枚举
     */
    @Override
    protected DomainMethodEnum getDomainMethod() {
        return DomainMethodEnum.INSERT;
    }

    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong 
     * @return 计算方法名称
     */
    @Override
    protected String calculateMethodName() {
        return DomainMethodEnum.INSERT.getValue();
    }


}
