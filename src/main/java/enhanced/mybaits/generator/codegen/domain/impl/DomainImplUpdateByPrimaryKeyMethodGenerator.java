
package enhanced.mybaits.generator.codegen.domain.impl;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.IEnhanceCommentGenerator;
import enhanced.mybaits.generator.codegen.domain.AbstractDomainImplMethodGenerator;
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
 * Domain 接口实现类更新指定记录方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class DomainImplUpdateByPrimaryKeyMethodGenerator extends AbstractDomainImplMethodGenerator {

    public DomainImplUpdateByPrimaryKeyMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 增加方法内容
     * @author 徐明龙 XuMingLong 
     * @param method 基于的方法
     */
    @Override
    protected void addMethodBody(Method method) {
        StringBuilder sb = new StringBuilder();
        //增加返回结果
        sb.setLength(0);
        sb.append(repositoryFieldName);
        sb.append(".");
        sb.append(calculateMethodName());
        sb.append("(");
        sb.append(dTOVarName);
        sb.append(",");
        sb.append(userVarName);
        sb.append(",");
        sb.append(nowVarName);
        sb.append(");");
        method.addBodyLine(sb.toString());
        sb.setLength(0);
    }

    /**
     * 增加额外的方法
     * @author 徐明龙 XuMingLong 
     * @param method 基于的方法
     * @return 返回额外增加的方法
     */
    @Override
    protected List<Method> addExtraMethod(Method method) {
        List<Method> methodList = new ArrayList<>();
        Method verifyForUpdateMethod = new Method();
        verifyForUpdateMethod.addAnnotation("@Override");
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
        StringBuilder sb = new StringBuilder();
        sb.append(returnType.getShortName());
        sb.append(" ");
        sb.append(errorVarName);
        FullyQualifiedJavaType arrayListType = FullyQualifiedJavaType.getNewArrayListInstance();
        sb.append(" = new ");
        sb.append(arrayListType.getShortNameWithoutTypeArguments());
        sb.append("<>();");
        verifyForUpdateMethod.addBodyLine(sb.toString());
        verifyForUpdateMethod.addBodyLine("// TODO 补充表单校验内容");
        sb.setLength(0);
        sb.append("return ");
        sb.append(errorVarName);
        sb.append(";");
        verifyForUpdateMethod.addBodyLine(sb.toString());

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
     * 返回Domain方法枚举类
     * @author 徐明龙 XuMingLong 
     * @return 返回Domain方法对应的枚举
     */
    @Override
    protected DomainMethodEnum getDomainMethod() {
        return DomainMethodEnum.UPDATE_BY_PRIMARY_KEY;
    }

    
    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong
     * @return 返回计算后的方法名
     */
    @Override
    protected String calculateMethodName() {
        String subName = getJoinedKeyColumnListJavaPropertyName("And",true);
        return DomainMethodEnum.UPDATE_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName);
    }
}
