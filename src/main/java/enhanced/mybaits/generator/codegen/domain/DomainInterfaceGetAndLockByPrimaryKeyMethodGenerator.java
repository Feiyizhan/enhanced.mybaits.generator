
package enhanced.mybaits.generator.codegen.domain;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.enums.DomainMethodEnum;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.List;

/**
 * Domain 接口获取并锁定主键对应的记录的返回结果对象方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class DomainInterfaceGetAndLockByPrimaryKeyMethodGenerator extends AbstractDomainInterfaceMethodGenerator {

    public DomainInterfaceGetAndLockByPrimaryKeyMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 增加额外的方法
     * @author 徐明龙 XuMingLong
     * @param method 待处理的方法
     * @return 额外的方法
     */
    @Override protected List<Method> addExtraMethod(Method method) {
        return null;
    }

    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong 
     * @return 返回计算后的方法名称
     */
    @Override
    protected String calculateMethodName() {
        String subName = getJoinedKeyColumnListJavaPropertyName("And",true);
        return DomainMethodEnum.GET_AND_LOCK_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName);
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
            this.mixedContext.getDomainInterface().addImportedType(parameterType);
            
        });
        
    }

    /**
     * 设置返回参数
     * @author 徐明龙 XuMingLong 
     * @param method 待处理的方法
     */
    @Override
    protected void setMethodReturnType(Method method) {
        FullyQualifiedJavaType returnType = this.mixedContext.getDOClass().getType();
        method.setReturnType(returnType);
        this.mixedContext.getDomainInterface().addImportedType(returnType);
        
    }

    /**
     * 获取Domain方法对应的枚举
     * @author 徐明龙 XuMingLong 
     * @return 返回方法对应的枚举
     */
    @Override
    protected DomainMethodEnum getDomainMethod() {
        return DomainMethodEnum.GET_AND_LOCK_BY_PRIMARY_KEY;
    }


}
