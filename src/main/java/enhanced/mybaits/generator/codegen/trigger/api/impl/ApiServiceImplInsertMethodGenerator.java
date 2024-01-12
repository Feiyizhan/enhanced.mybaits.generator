
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
 * Api Service 接口实现类新增方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class ApiServiceImplInsertMethodGenerator extends AbstractApiServiceImplMethodGenerator {

    public ApiServiceImplInsertMethodGenerator(MixedContext mixedContext) {
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
        method.addBodyLine(getNewTypeCode(dTOType,dTOVarName));

        List<Field> formFieldList = this.mixedContext.getFormClass().getFields();
        List<Field> dTOFieldList = this.mixedContext.getDTOClass().getFields();
        Map<String,Field> formFieldMap = formFieldList.stream()
            .collect(Collectors.toMap(Field::getName, Function.identity()));
        for(Field dTOField:dTOFieldList) {
            Field formField = formFieldMap.get(dTOField.getName());
            if(formField!=null){
                method.addBodyLine(generateCopyFieldCode(dTOField,formField,dTOVarName,formVarName));
            }
        }
        //增加新增语句
        sb.setLength(0);
        sb.append("return ");
        sb.append(applicationFieldName);
        sb.append(".");
        sb.append(calculateMethodName());
        sb.append("(");
        sb.append(dTOVarName);
        sb.append(",");
        sb.append(userVarName);
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
        return null;
    }


    /**
     * 返回ApiService方法枚举类
     * @author 徐明龙 XuMingLong 
     * @return 返回ApiService方法枚举类
     */
    @Override
    protected ApiServiceMethodEnum getApiServiceMethod() {
        return ApiServiceMethodEnum.INSERT;
    }

    
    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong
     * @return 返回计算后的方法名
     */
    @Override
    protected String calculateMethodName() {
        return ApiServiceMethodEnum.INSERT.getValue();
    }
}
