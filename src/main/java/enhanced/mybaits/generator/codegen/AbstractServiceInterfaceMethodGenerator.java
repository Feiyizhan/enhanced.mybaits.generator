/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.codegen;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.enums.ServiceMethodEnum;

/**
 * Service 接口方法生成器基类
 * @author 徐明龙 XuMingLong 
 * @createDate 2018-11-20 
 */
public abstract class AbstractServiceInterfaceMethodGenerator extends AbstractMethodGenerator {
    /**
     * form 参数名称
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     */
    protected static final String formParameterName = "form";
    
    /**
     * 用户参数名
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     */
    protected static final String userParameterName = "user";

    public AbstractServiceInterfaceMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 增加方法
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-20 
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
     * @createDate 2018-11-21 
     * @return
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
     * @createDate 2018-11-21 
     * @param method
     */
    protected abstract void setMethodReturnType(Method method);

    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     * @return
     */
    protected abstract String calculateMethodName() ;
    
    /**
     * 获取Service方法名
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-23 
     * @return
     */
    protected abstract ServiceMethodEnum getServiceMethod() ;
    
    /**
     * 增加方法内容
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     * @param method
     */
    protected abstract void addMethodParameter(Method method) ;

}
