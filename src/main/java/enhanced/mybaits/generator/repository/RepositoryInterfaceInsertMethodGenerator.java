
package enhanced.mybaits.generator.repository;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.enums.RepositoryMethodEnum;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

/**
 * Repository 接口新增方法生成器
 * @author 徐明龙 XuMingLong
 */
public class RepositoryInterfaceInsertMethodGenerator extends AbstractRepositoryInterfaceMethodGenerator {

    public RepositoryInterfaceInsertMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 增加方法参数
     * @author 徐明龙 XuMingLong
     * @param method 待处理的方法
     */
    @Override
    protected void addMethodParameter(Method method) {
        FullyQualifiedJavaType dTOType = this.mixedContext.getDTOClass().getType();
        Parameter dTOParameter = new Parameter(dTOType, dTOVarName);
        method.addParameter(dTOParameter);
        this.mixedContext.getRepositoryInterface().addImportedType(dTOType);

        FullyQualifiedJavaType userType = new FullyQualifiedJavaType(getUserClassName());
        Parameter userParameter = new Parameter(userType, userVarName);
        method.addParameter(userParameter);
        this.mixedContext.getRepositoryInterface().addImportedType(userType);

        FullyQualifiedJavaType nowType = new FullyQualifiedJavaType(getNowClassName());
        Parameter nowParameter = new Parameter(nowType, nowVarName);
        method.addParameter(nowParameter);
        this.mixedContext.getRepositoryInterface().addImportedType(nowType);
    }

    /**
     * 设置返回参数
     * @author 徐明龙 XuMingLong
     * @param method 待处理的方法
     */
    @Override
    protected void setMethodReturnType(Method method) {
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(getIdClassName());
        method.setReturnType(returnType);
        this.mixedContext.getRepositoryInterface().addImportedType(returnType);
    }

    /**
     * 获取Repository方法对应的枚举
     * @author 徐明龙 XuMingLong
     * @return 返回方法对应的枚举
     */
    @Override
    protected RepositoryMethodEnum getRepositoryMethod() {
        return RepositoryMethodEnum.INSERT;
    }

    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong
     * @return 计算方法名称
     */
    @Override
    protected String calculateMethodName() {
        return RepositoryMethodEnum.INSERT.getValue();
    }


}
