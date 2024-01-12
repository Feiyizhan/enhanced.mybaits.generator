
package enhanced.mybaits.generator.codegen.application.impl;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.application.AbstractApplicationImplMethodGenerator;
import enhanced.mybaits.generator.enums.ApplicationMethodEnum;
import enhanced.mybaits.generator.enums.DomainImplExtraMethodEnum;
import enhanced.mybaits.generator.enums.DomainMethodEnum;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;

import java.util.List;

/**
 * Application 接口实现类更新指定记录方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class ApplicationImplUpdateByPrimaryKeyMethodGenerator extends AbstractApplicationImplMethodGenerator {

    public ApplicationImplUpdateByPrimaryKeyMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 增加方法内容
     * @author 徐明龙 XuMingLong 
     * @param method 基于的方法
     */
    @Override
    protected void addMethodBody(Method method) {
        FullyQualifiedJavaType listFormValidErrorType = getListFormValidErrorType();

        StringBuilder sb = new StringBuilder();
        sb.append("// 读取并锁定记录");
        method.addBodyLine(sb.toString());
        //增加读取并锁定记录代码
        FullyQualifiedJavaType dOType = this.mixedContext.getDOClass().getType();
        sb.setLength(0);
        sb.append(dOType.getShortNameWithoutTypeArguments());
        sb.append(" ");
        sb.append(dOVarName);
        sb.append(" = ");
        sb.append(domainFieldName);
        sb.append(".");
        String subName = getJoinedKeyColumnListJavaPropertyName("And",true);
        sb.append(DomainMethodEnum.GET_AND_LOCK_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName));
        sb.append("(");
        sb.append(getJoinedKeyColumnListJavaPropertyName(",",false));
        sb.append(");");
        method.addBodyLine(sb.toString());


        sb.setLength(0);
        sb.append("// 修改校验");
        method.addBodyLine(sb.toString());

        //增加调用编辑校验的代码
        sb.setLength(0);
        sb.append(listFormValidErrorType.getShortName());
        sb.append(" ");
        sb.append(errorVarName);
        sb.append(" = ");
        sb.append(domainFieldName);
        sb.append(".");
        sb.append(DomainImplExtraMethodEnum.VERIFY_FOR_UPDATE.getValue());
        sb.append("(");
        sb.append(dOVarName);
        sb.append(",");
        sb.append(dTOVarName);
        sb.append(",");
        sb.append(userVarName);
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

        //调用领域的更新代码
        sb.setLength(0);
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


        method.addBodyLine("}");
        //增加返回结果
        sb.setLength(0);
        sb.append("return ");
        sb.append(errorVarName);
        sb.append(";");
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
        return null;
    }


    /**
     * 返回Application方法枚举类
     * @author 徐明龙 XuMingLong 
     * @return 返回Application方法对应的枚举
     */
    @Override
    protected ApplicationMethodEnum getApplicationMethod() {
        return ApplicationMethodEnum.UPDATE_BY_PRIMARY_KEY;
    }

    
    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong
     * @return 返回计算后的方法名
     */
    @Override
    protected String calculateMethodName() {
        String subName = getJoinedKeyColumnListJavaPropertyName("And",true);
        return ApplicationMethodEnum.UPDATE_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName);
    }
}
