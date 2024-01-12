
package enhanced.mybaits.generator.codegen.trigger.api;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.enums.ApiServiceMethodEnum;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.List;

/**
 * Api Service 接口获取主键对应的记录方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class ApiServiceInterfaceGetByPrimaryKeyMethodGenerator extends AbstractApiServiceInterfaceMethodGenerator {

    public ApiServiceInterfaceGetByPrimaryKeyMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }


    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong 
     * @return 返回计算后的方法名称
     */
    @Override
    protected String calculateMethodName() {
        String subName = getJoinedKeyColumnListJavaPropertyName("And",true);
        return ApiServiceMethodEnum.GET_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName);
    }

    /**
     * 增加方法参数
     * @author 徐明龙 XuMingLong 
     * @param method 待处理的方法
     */
    @Override
    protected void addMethodParameter(Method method) {
        List<IntrospectedColumn> keyColumnList = this.introspectedTable.getPrimaryKeyColumns();
        keyColumnList.forEach((r)->{
            FullyQualifiedJavaType parameterType = r.getFullyQualifiedJavaType();
            Parameter parameter = new Parameter(parameterType,
                StringUtils.uncapitalize(r.getJavaProperty()));
            method.addParameter(parameter);
            this.mixedContext.getApiServiceInterface().addImportedType(parameterType);
            
        });
        
    }

    /**
     * 设置返回参数
     * @author 徐明龙 XuMingLong 
     * @param method 待处理的方法
     */
    @Override
    protected void setMethodReturnType(Method method) {
        FullyQualifiedJavaType returnType = this.mixedContext.getResClass().getType();
        method.setReturnType(returnType);
        this.mixedContext.getApiServiceInterface().addImportedType(returnType);
        
    }

    /**
     * 获取Api Service方法对应的枚举
     * @author 徐明龙 XuMingLong 
     * @return 返回方法对应的枚举
     */
    @Override
    protected ApiServiceMethodEnum getApiServiceMethod() {
        return ApiServiceMethodEnum.GET_BY_PRIMARY_KEY;
    }


}
