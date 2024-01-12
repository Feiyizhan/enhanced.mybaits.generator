
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
 * Domain 接口实现类新增方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class DomainImplInsertMethodGenerator extends AbstractDomainImplMethodGenerator {

    public DomainImplInsertMethodGenerator(MixedContext mixedContext) {
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
        sb.append("return ");
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
     * @return 增加的额外方法
     */
    @Override
    protected List<Method> addExtraMethod(Method method) {
        List<Method> methodList = new ArrayList<>();
        Method verifyFromForInsertMethod = new Method();
        verifyFromForInsertMethod.addAnnotation("@Override");
        verifyFromForInsertMethod.setName(DomainImplExtraMethodEnum.VERIFY_FOR_INSERT.getValue());
        //参数
        FullyQualifiedJavaType dTOType = this.mixedContext.getDTOClass().getType();
        Parameter dTOParameter = new Parameter(dTOType, dTOVarName);
        verifyFromForInsertMethod.addParameter(dTOParameter);
        this.mixedContext.getRepositoryInterface().addImportedType(dTOType);

        FullyQualifiedJavaType userType = new FullyQualifiedJavaType(getUserClassName());
        Parameter userParameter = new Parameter(userType, userVarName);
        verifyFromForInsertMethod.addParameter(userParameter);
        this.mixedContext.getRepositoryInterface().addImportedType(userType);

        verifyFromForInsertMethod.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = getListFormValidErrorType();
        //增加返回参数
        verifyFromForInsertMethod.setReturnType(returnType);
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
        verifyFromForInsertMethod.addBodyLine(sb.toString());
        verifyFromForInsertMethod.addBodyLine("// TODO 补充表单校验内容");
        sb.setLength(0);
        sb.append("return ");
        sb.append(errorVarName);
        sb.append(";");
        verifyFromForInsertMethod.addBodyLine(sb.toString());

        //生成注释
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        CommentGenerator commentGenerator = context.getCommentGenerator();
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
            enhanceCommentGenerator.addDomainExtraMethodComment(verifyFromForInsertMethod);
        }

        methodList.add(verifyFromForInsertMethod);
        return methodList;
    }


    /**
     * 返回Domain方法枚举类
     * @author 徐明龙 XuMingLong 
     * @return 返回Domain方法枚举类
     */
    @Override
    protected DomainMethodEnum getDomainMethod() {
        return DomainMethodEnum.INSERT;
    }

    
    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong
     * @return 返回计算后的方法名
     */
    @Override
    protected String calculateMethodName() {
        return DomainMethodEnum.INSERT.getValue();
    }
}
