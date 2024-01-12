
package enhanced.mybaits.generator.codegen.application.impl;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.application.AbstractApplicationImplMethodGenerator;
import enhanced.mybaits.generator.enums.ApplicationMethodEnum;
import enhanced.mybaits.generator.enums.DomainImplExtraMethodEnum;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;

import java.util.List;

/**
 * Application 接口实现类新增方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class ApplicationImplInsertMethodGenerator extends AbstractApplicationImplMethodGenerator {

    public ApplicationImplInsertMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 增加方法内容
     * @author 徐明龙 XuMingLong 
     * @param method 基于的方法
     */
    @Override
    protected void addMethodBody(Method method) {
        FullyQualifiedJavaType returnType = method.getReturnType();
        method.addBodyLine(getNewTypeCode(returnType,returnVarName));
        FullyQualifiedJavaType listFormValidErrorType = getListFormValidErrorType();

        StringBuilder sb = new StringBuilder();
        sb.append("// 表单校验");
        method.addBodyLine(sb.toString());

        //增加调用新增校验的代码
        sb.setLength(0);
        sb.append(listFormValidErrorType.getShortName());
        sb.append(" ");
        sb.append(errorVarName);
        sb.append(" = ");
        sb.append(domainFieldName);
        sb.append(".");
        sb.append(DomainImplExtraMethodEnum.VERIFY_FOR_INSERT.getValue());
        sb.append("(");
        sb.append(dTOVarName);
        sb.append(",");
        sb.append(userVarName);
        sb.append(");");
        method.addBodyLine(sb.toString());

        sb.setLength(0);
        sb.append(returnVarName);
        sb.append(".setErrors(");
        sb.append(errorVarName);
        sb.append(");");
        method.addBodyLine(sb.toString());

        sb.setLength(0);
        sb.append("if (CollectionUtils.isEmpty(");
        sb.append(errorVarName);
        sb.append(")) {");
        method.addBodyLine(sb.toString());

        sb.setLength(0);
        sb.append("LocalDateTime ");
        sb.append(nowVarName);
        sb.append(" = now();");
        method.addBodyLine(sb.toString());

        //调用领域的新增代码
        FullyQualifiedJavaType idType = new FullyQualifiedJavaType(getIdClassName());
        String idVarName = getAutoIncrementKeyName();
        sb.setLength(0);
        sb.append(idType.getShortName());
        sb.append(" ");
        sb.append(idVarName);
        sb.append(" = ");
        sb.append(domainFieldName);
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

        //增加获取新增自增id处理
        sb.setLength(0);
        sb.append(returnVarName);
        sb.append(".setResult(");
        sb.append(idVarName);
        sb.append(");");
        method.addBodyLine(sb.toString());

        method.addBodyLine("}");
        //增加返回结果
        sb.setLength(0);
        sb.append("return ");
        sb.append(returnVarName);
        sb.append(";");
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
        return null;
    }


    /**
     * 返回Application方法枚举类
     * @author 徐明龙 XuMingLong 
     * @return 返回Application方法枚举类
     */
    @Override
    protected ApplicationMethodEnum getApplicationMethod() {
        return ApplicationMethodEnum.INSERT;
    }

    
    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong
     * @return 返回计算后的方法名
     */
    @Override
    protected String calculateMethodName() {
        return ApplicationMethodEnum.INSERT.getValue();
    }
}
