package enhanced.mybaits.generator.codegen.trigger.api.impl;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.trigger.api.AbstractApiServiceImplMethodGenerator;
import enhanced.mybaits.generator.enums.ApiServiceMethodEnum;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ApiService 接口实现类获取主键对应的记录方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class ApiServiceImplGetByPrimaryKeyMethodGenerator extends AbstractApiServiceImplMethodGenerator {

    public ApiServiceImplGetByPrimaryKeyMethodGenerator(MixedContext mixedContext) {
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
        FullyQualifiedJavaType dTOType = this.mixedContext.getDTOClass().getType();
        sb.setLength(0);
        sb.append(dTOType.getShortNameWithoutTypeArguments());
        sb.append(" ");
        sb.append(dTOVarName);
        sb.append(" = ");
        sb.append(applicationFieldName);
        sb.append(".");
        sb.append(calculateMethodName());
        sb.append("(");
        sb.append(getJoinedKeyColumnListJavaPropertyName(",",false));
        sb.append(");");
        method.addBodyLine(sb.toString());

        //增加判断是否为空
        sb.setLength(0);
        sb.append("if(");
        sb.append(dTOVarName);
        sb.append(" == null){");
        method.addBodyLine(sb.toString());
        method.addBodyLine("return null;");
        method.addBodyLine("}");

        FullyQualifiedJavaType resType = this.mixedContext.getResClass().getType();
        //增加 New Res对象的代码
        method.addBodyLine(getNewTypeCode(resType,resVarName));

        List<Field> resFieldList = this.mixedContext.getResClass().getFields();
        List<Field> dTOFieldList = this.mixedContext.getDTOClass().getFields();
        Map<String,Field> dTOFieldMap = dTOFieldList.stream()
            .collect(Collectors.toMap(Field::getName, Function.identity()));
        for(Field resField:resFieldList) {
            Field dTOField = dTOFieldMap.get(resField.getName());
            if(dTOField!=null){
                method.addBodyLine(generateCopyFieldCode(resField,dTOField,resVarName,dTOVarName));
            }
        }
        //增加返回结果
        sb.setLength(0);
        sb.append("return ");
        sb.append(resVarName);
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
     * 返回ApiService方法枚举类
     * @author 徐明龙 XuMingLong 
     * @return 返回ApiService方法枚举类
     */
    @Override
    protected ApiServiceMethodEnum getApiServiceMethod() {
        return ApiServiceMethodEnum.GET_BY_PRIMARY_KEY;
    }

    
    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong
     * @return 计算方法名称
     */
    @Override
    protected String calculateMethodName() {
        String subName = getJoinedKeyColumnListJavaPropertyName("And",true);
        return ApiServiceMethodEnum.GET_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName);
    }
}
