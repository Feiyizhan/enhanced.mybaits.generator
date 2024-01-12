
package enhanced.mybaits.generator.codegen.domain;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.IEnhanceCommentGenerator;
import enhanced.mybaits.generator.enums.DomainImplExtraMethodEnum;
import enhanced.mybaits.generator.enums.DomainMethodEnum;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain 接口更新指定记录方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class DomainInterfaceUpdateByPrimaryKeyMethodGenerator extends AbstractDomainInterfaceMethodGenerator {

    public DomainInterfaceUpdateByPrimaryKeyMethodGenerator(MixedContext mixedContext) {
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
        Method verifyForUpdateMethod = new Method();
        verifyForUpdateMethod.setName(DomainImplExtraMethodEnum.VERIFY_FOR_UPDATE.getValue());

        //参数
        FullyQualifiedJavaType dOType = this.mixedContext.getDOClass().getType();
        Parameter dOParameter = new Parameter(dOType, dOVarName);
        verifyForUpdateMethod.addParameter(dOParameter);
        this.mixedContext.getRepositoryInterface().addImportedType(dOType);

        FullyQualifiedJavaType dTOType = this.mixedContext.getDTOClass().getType();
        Parameter dTOParameter = new Parameter(dTOType, dTOVarName);
        verifyForUpdateMethod.addParameter(dTOParameter);
        this.mixedContext.getRepositoryInterface().addImportedType(dTOType);

        FullyQualifiedJavaType userType = new FullyQualifiedJavaType(getUserClassName());
        Parameter userParameter = new Parameter(userType, userVarName);
        verifyForUpdateMethod.addParameter(userParameter);
        this.mixedContext.getRepositoryInterface().addImportedType(userType);

        verifyForUpdateMethod.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = getListFormValidErrorType();
        //增加返回参数
        verifyForUpdateMethod.setReturnType(returnType);
        returnType.getImportList().forEach((r)->{
            this.mixedContext.getDomainInterface().addImportedType(new FullyQualifiedJavaType(r));
        });

        //生成注释
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        CommentGenerator commentGenerator = context.getCommentGenerator();
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
            enhanceCommentGenerator.addDomainExtraMethodComment(verifyForUpdateMethod);
        }
        methodList.add(verifyForUpdateMethod);
        return methodList;
    }

    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong 
     * @return 返回计算的方法名称
     */
    @Override
    protected String calculateMethodName() {
        String subName = getJoinedKeyColumnListJavaPropertyName("And",true);
        return DomainMethodEnum.UPDATE_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName);
    }

    /**
     * 增加方法参数
     * @author 徐明龙 XuMingLong 
     * @param method 基于的方法
     */
    @Override
    protected void addMethodParameter(Method method) {
        FullyQualifiedJavaType dTOType = this.mixedContext.getDTOClass().getType();
        Parameter dTOParameter = new Parameter(dTOType, dTOVarName);
        method.addParameter(dTOParameter);
        this.mixedContext.getDomainInterface().addImportedType(dTOType);

        FullyQualifiedJavaType userType = new FullyQualifiedJavaType(getUserClassName());
        Parameter userParameter = new Parameter(userType, userVarName);
        method.addParameter(userParameter);
        this.mixedContext.getDomainInterface().addImportedType(userType);

        FullyQualifiedJavaType nowType = new FullyQualifiedJavaType(getNowClassName());
        Parameter nowParameter = new Parameter(nowType, nowVarName);
        method.addParameter(nowParameter);
        this.mixedContext.getDomainInterface().addImportedType(nowType);

    }

    /**
     * 设置返回参数
     * @author 徐明龙 XuMingLong 
     * @param method 基于的方法
     */
    @Override
    protected void setMethodReturnType(Method method) {
        method.setReturnType(null);
    }

    /**
     * 获取Domain方法名枚举类
     * @author 徐明龙 XuMingLong 
     * @return 返回方法对应的枚举
     */
    @Override
    protected DomainMethodEnum getDomainMethod() {
        return DomainMethodEnum.UPDATE_BY_PRIMARY_KEY;
    }


}
