package enhanced.mybaits.generator.codegen.application.impl;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.application.AbstractApplicationImplMethodGenerator;
import enhanced.mybaits.generator.enums.ApplicationMethodEnum;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Application 接口实现类获取主键对应的记录方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class ApplicationImplGetByPrimaryKeyMethodGenerator extends AbstractApplicationImplMethodGenerator {

    public ApplicationImplGetByPrimaryKeyMethodGenerator(MixedContext mixedContext) {
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
        FullyQualifiedJavaType dOType = this.mixedContext.getDOClass().getType();
        sb.setLength(0);
        sb.append(dOType.getShortNameWithoutTypeArguments());
        sb.append(" ");
        sb.append(dOVarName);
        sb.append(" = ");
        sb.append(domainFieldName);
        sb.append(".");
        sb.append(calculateMethodName());
        sb.append("(");
        sb.append(getJoinedKeyColumnListJavaPropertyName(",",false));
        sb.append(");");
        method.addBodyLine(sb.toString());

        FullyQualifiedJavaType dTOType = this.mixedContext.getDTOClass().getType();
        //增加 New DTO对象的代码
        method.addBodyLine(getNewTypeCode(dTOType,dTOVarName));

        List<Field> dOFieldList = this.mixedContext.getDOClass().getFields();
        List<Field> dTOFieldList = this.mixedContext.getDTOClass().getFields();
        Map<String,Field> dOFieldMap = dOFieldList.stream()
            .collect(Collectors.toMap(Field::getName, Function.identity()));
        for(Field dtoField:dTOFieldList) {
            Field dOField = dOFieldMap.get(dtoField.getName());
            if(dOField!=null){
                method.addBodyLine(generateCopyFieldCode(dtoField,dOField,dTOVarName,dOVarName));
            }
        }
        //增加返回结果
        sb.setLength(0);
        sb.append("return ");
        sb.append(dTOVarName);
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
     * @return 返回Application方法枚举类
     */
    @Override
    protected ApplicationMethodEnum getApplicationMethod() {
        return ApplicationMethodEnum.GET_BY_PRIMARY_KEY;
    }

    
    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong
     * @return 计算方法名称
     */
    @Override
    protected String calculateMethodName() {
        String subName = getJoinedKeyColumnListJavaPropertyName("And",true);
        return ApplicationMethodEnum.GET_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName);
    }
}
