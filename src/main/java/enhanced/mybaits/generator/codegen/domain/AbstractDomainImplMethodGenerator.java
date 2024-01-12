
package enhanced.mybaits.generator.codegen.domain;


import enhanced.mybaits.generator.CodeGeneratorUtils;
import enhanced.mybaits.generator.MixedContext;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;

import java.util.List;

/**
 * Domain 接口实现类方法生成器基类
 * @author 徐明龙 XuMingLong
 */
public abstract class AbstractDomainImplMethodGenerator extends AbstractDomainInterfaceMethodGenerator {

    /**
     * 错误返回的变量名
     * @author 徐明龙 XuMingLong 2024-01-12
     */
    protected static final String errorVarName = "errors";
    /**
     * Repository字段名称
     * @author 徐明龙 XuMingLong
     */
    protected String repositoryFieldName;
    /**
     * 对应的接口方法
     * @author 徐明龙 XuMingLong
     */
    protected Method interfaceMethod;

    public AbstractDomainImplMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
        this.repositoryFieldName = getRepositoryFieldName();
    }



    /**
     * 获取表对应的实体类变量名称
     * @author 徐明龙 XuMingLong
     * @return 返回基础实体变量名
     */
    protected String getBaseRecordVarName() {
        return StringUtils.uncapitalize(this.mixedContext.getBaseRecord().getType().getShortNameWithoutTypeArguments());
    }

    /**
     * 增加方法
     * @author 徐明龙 XuMingLong
     */
    @Override
    public void addMethod() {
        //设置对应的接口方法
        setInterfaceMethod();
        Method method =null ;
        if(interfaceMethod!=null) {
            method = new Method(interfaceMethod);
            method.setVisibility(JavaVisibility.PUBLIC);
            method.addAnnotation("@Override");
            //增加方法内容
            addMethodBody(method);
            this.mixedContext.getDomainImplClass().addMethod(method);
        }

        List<Method> extraMethodList = addExtraMethod(method);
        if(extraMethodList!=null && extraMethodList.size() >0) {
            extraMethodList.forEach((r)->{
                this.mixedContext.getDomainImplClass().addMethod(r);
            });
        }
    }




    /**
     * 增加额外的方法
     * @author 徐明龙 XuMingLong
     * @param method 待处理的方法
     * @return 额外的方法
     */
    @Override
    protected abstract List<Method> addExtraMethod(Method method);

    /**
     * 增加方法内容
     * @author 徐明龙 XuMingLong
     * @param method 待处理的方法
     */
    protected abstract void addMethodBody(Method method) ;

    /**
     * 设置对应的接口方法
     * @author 徐明龙 XuMingLong
     */
    protected void setInterfaceMethod() {
        String methodName = calculateMethodName();
        List<Method> methodList = this.mixedContext.getDomainInterface().getMethods();
        for(Method r:methodList) {
            if(r.getName().equals(methodName)) {
                this.interfaceMethod = r;
                break;
            }
        }
    }


    /**
     * 设置方法返回类型
     * @author 徐明龙 XuMingLong
     * @param method 待处理的方法
     */
    @Override
    protected void setMethodReturnType(Method method) {

    }

    /**
     * 增加方法参数
     * @author 徐明龙 XuMingLong
     * @param method 待处理的方法
     */
    @Override
    protected void addMethodParameter(Method method) {

    }
    

    /**
     * 获取Repository字段名称
     * @author 徐明龙 XuMingLong 
     * @return 待注入的Mapper字段名称
     */
    protected String getRepositoryFieldName() {
        return CodeGeneratorUtils.getInterfaceVarName(this.mixedContext.getRepositoryInterface());
    }

}
