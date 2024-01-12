
package enhanced.mybaits.generator.codegen.trigger.api;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstractMethodGenerator;
import enhanced.mybaits.generator.codegen.IEnhanceCommentGenerator;
import enhanced.mybaits.generator.enums.ApiServiceMethodEnum;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.Method;

/**
 * Api Service 接口方法生成器基类
 * @author 徐明龙 XuMingLong 
 */
public abstract class AbstractApiServiceInterfaceMethodGenerator extends AbstractMethodGenerator {
    /**
     * dTO 变量名称后缀
     * @author 徐明龙 XuMingLong
     */
    protected static final String dTOVarNameSuffix = "DTO";

    /**
     * dO 参数名称后缀
     * @author 徐明龙 XuMingLong
     */
    protected static final String dOVarNameSuffix = "DO";

    /**
     * 用户变量名
     * @author 徐明龙 XuMingLong
     */
    protected static final String userVarName = "user";

    /**
     * 时间变量名
     * @author 徐明龙 XuMingLong
     */
    protected static final String nowVarName = "now";

    /**
     * 获取表对应的DTO变量名称
     * @author 徐明龙 XuMingLong
     */
    protected String dTOVarName ;

    /**
     * 获取表对应的DO变量名称
     * @author 徐明龙 XuMingLong
     */
    protected String dOVarName ;

    /**
     * 获取表对应的Form变量名称
     * @author 徐明龙 XuMingLong
     */
    protected String formVarName ;

    /**
     * 获取表对应的Res变量名称
     * @author 徐明龙 XuMingLong
     */
    protected String resVarName ;


    public AbstractApiServiceInterfaceMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
        this.dTOVarName = StringUtils.uncapitalize(mixedContext.getDTOClass().getType().getShortNameWithoutTypeArguments());
        this.dOVarName = StringUtils.uncapitalize(mixedContext.getDOClass().getType().getShortNameWithoutTypeArguments());
        this.formVarName = StringUtils.uncapitalize(mixedContext.getFormClass().getType().getShortNameWithoutTypeArguments());
        this.resVarName = StringUtils.uncapitalize(mixedContext.getResClass().getType().getShortNameWithoutTypeArguments());
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
            enhanceCommentGenerator.addApiServiceMethodComment(method, getApiServiceMethod());
        }
        mixedContext.getApiServiceInterface().addMethod(method);

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
     * 获取Api Service方法对应的枚举
     * @author 徐明龙 XuMingLong 
     * @return 返回方法对应的枚举
     */
    protected abstract ApiServiceMethodEnum getApiServiceMethod() ;
    
    /**
     * 增加方法参数
     * @author 徐明龙 XuMingLong 
     * @param method 待处理的方法
     */
    protected abstract void addMethodParameter(Method method) ;

}
