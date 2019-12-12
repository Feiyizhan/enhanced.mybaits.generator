
package enhanced.mybaits.generator.codegen;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.enums.ServiceMethodEnum;

/**
 * Service 接口方法生成器基类
 * @author 徐明龙 XuMingLong 
 */
public abstract class AbstractServiceInterfaceMethodGenerator extends AbstractMethodGenerator {
    /**
     * form 参数名称
     * @author 徐明龙 XuMingLong 
     */
    protected static final String formParameterName = "form";
    
    /**
     * 用户参数名
     * @author 徐明龙 XuMingLong 
     */
    protected static final String userParameterName = "user";

    public AbstractServiceInterfaceMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 增加方法
     * @author 徐明龙 XuMingLong 
     */
    @Override
    public void addMethod() {
        Method method = new Method();
        method.setName(calculateMethodName());
        //设置返回参数
        setMethodReturnType(method);
        //增加方法参数
        addMethodParameter(method);
        //生成注释
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        CommentGenerator commentGenerator = context.getCommentGenerator();
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
            enhanceCommentGenerator.addServiceMethodComment(method,getServiceMethod());
        }
        mixedContext.getServiceInterface().addMethod(method);
    }
    

    
    /**
     * 获取FormValidError List对象的类型
     * @author 徐明龙 XuMingLong 
     * @return 返回List的表单校验结果类型对象
     */
    protected FullyQualifiedJavaType getListFormValidErrorType() {
        FullyQualifiedJavaType type = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType formValidErrorType = new FullyQualifiedJavaType(getFormValidErrorClassName());
        type.addTypeArgument(formValidErrorType);
        return type;
    }
    
    /**
     * 设置返回参数
     * @author 徐明龙 XuMingLong 
     * @param method 待处理的方法
     */
    protected abstract void setMethodReturnType(Method method);

    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong 
     * @return 返回计算后的方法名称
     */
    protected abstract String calculateMethodName() ;
    
    /**
     * 获取Service方法对应的枚举
     * @author 徐明龙 XuMingLong 
     * @return 返回方法对应的枚举
     */
    protected abstract ServiceMethodEnum getServiceMethod() ;
    
    /**
     * 增加方法参数
     * @author 徐明龙 XuMingLong 
     * @param method 待处理的方法
     */
    protected abstract void addMethodParameter(Method method) ;

}
