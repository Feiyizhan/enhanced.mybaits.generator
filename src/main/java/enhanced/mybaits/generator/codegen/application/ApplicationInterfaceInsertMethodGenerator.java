
package enhanced.mybaits.generator.codegen.application;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.enums.ApplicationMethodEnum;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;

/**
 * Application 接口新增方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class ApplicationInterfaceInsertMethodGenerator extends AbstractApplicationInterfaceMethodGenerator {

    public ApplicationInterfaceInsertMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 设置返回参数
     * @author 徐明龙 XuMingLong 
     * @param method 待处理的方法
     */
    @Override
    protected void addMethodParameter(Method method) {
        FullyQualifiedJavaType dTOType = this.mixedContext.getDTOClass().getType();
        Parameter dTOParameter = new Parameter(dTOType, dTOVarName);
        method.addParameter(dTOParameter);
        this.mixedContext.getApplicationInterface().addImportedType(dTOType);

        FullyQualifiedJavaType userType = new FullyQualifiedJavaType(getUserClassName());
        Parameter userParameter = new Parameter(userType, userVarName);
        method.addParameter(userParameter);
        this.mixedContext.getApplicationInterface().addImportedType(userType);

    }

    /**
     * 设置返回参数
     * @author 徐明龙 XuMingLong 
     * @param method 待处理的方法
     */
    @Override
    protected void setMethodReturnType(Method method) {
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(getStandardCheckAndHandleDtoClassName());
        returnType.addTypeArgument(PrimitiveTypeWrapper.getIntegerInstance());
        method.setReturnType(returnType);
//        returnType.getImportList().forEach((r)->{
//            this.mixedContext.getApplicationInterface().addImportedType(new FullyQualifiedJavaType(r));
//        });
        this.mixedContext.getApplicationInterface().addImportedType(
            new FullyQualifiedJavaType(returnType.getFullyQualifiedNameWithoutTypeParameters()));
    }

    /**
     * 获取Application方法对应的枚举
     * @author 徐明龙 XuMingLong 
     * @return 返回方法对应的枚举
     */
    @Override
    protected ApplicationMethodEnum getApplicationMethod() {
        return ApplicationMethodEnum.INSERT;
    }

    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong 
     * @return 计算方法名称
     */
    @Override
    protected String calculateMethodName() {
        return ApplicationMethodEnum.INSERT.getValue();
    }


}
