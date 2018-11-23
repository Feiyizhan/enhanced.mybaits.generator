/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.codegen;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;


import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.enums.AudiFieldEnum;

/**
 * Service 接口实现类方法生成器基类
 * @author 徐明龙 XuMingLong 
 * @createDate 2018-11-21 
 */
public abstract class AbstractServiceImplMethodGenerator extends AbstractServiceInterfaceMethodGenerator {

    protected static final String returnVarName = "result";
    protected static final String errorVarName = "errors";
    protected static final String nowVarName = "now";

    
    /**
     * 获取表对应的实体类变量名称
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     */
    protected String baseRecordVarName ;
    /**
     * Mapper字段名称
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     */
    protected String mapperFieldName;
    /**
     * 对应的接口方法
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     */
    protected Method interfaceMethod;
    
    public AbstractServiceImplMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
        this.baseRecordVarName = getBaseRecordVarName();
        this.mapperFieldName = getMapperFieldName();
    }
    
    

    /**
     * 获取表对应的实体类变量名称
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     * @return
     */
    protected String getBaseRecordVarName() {
        return StringUtils.uncapitalize(this.mixedContext.getBaseRecord().getType().getShortNameWithoutTypeArguments());
    }

    /**
     * 获取表对应的实体类的类型
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     * @return
     */
    protected FullyQualifiedJavaType getBaseRecordType() {
        return this.mixedContext.getBaseRecord().getType();
    }

    /**
     * 增加方法
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
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
            this.mixedContext.getServiceImplClass().addMethod(method);
        }
        
        List<Method> extraMethodList = addExtraMethod(method);
        if(extraMethodList!=null && extraMethodList.size() >0) {
            extraMethodList.forEach((r)->{
                this.mixedContext.getServiceImplClass().addMethod(r);
            });
        }
    }
    
    
    

    /**
     * 增加额外的方法
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     * @param method
     * @return
     */
    protected abstract List<Method> addExtraMethod(Method method);
    
    /**
     * 增加方法内容
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     * @param method
     */
    protected abstract void addMethodBody(Method method) ;
    
    /**
     * 设置对应的接口方法
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     */
    protected void setInterfaceMethod() {
        String methodName = calculateMethodName();
        List<Method> methodList = this.mixedContext.getServiceInterface().getMethods();
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
     * @createDate 2018-11-23
     * @param method
     */
    @Override
    protected void setMethodReturnType(Method method) {

    }

    /**
     * 增加方法参数
     * @author 徐明龙 XuMingLong
     * @createDate 2018-11-23
     * @param method
     */
    @Override
    protected void addMethodParameter(Method method) {

    }
    
    /**
     * 获取New 类型代码
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     * @param type
     * @param varName
     * @return
     */
    protected String getNewTypeCode(FullyQualifiedJavaType type,String varName) {
        //判断是否有泛型
        boolean isGeneric= false;
        if(type.getTypeArguments()!=null && !type.getTypeArguments().isEmpty()) {
            isGeneric = true;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(type.getShortName());
        sb.append(" ");
        if(StringUtils.isBlank(varName)) {
            sb.append(StringUtils.uncapitalize(type.getShortNameWithoutTypeArguments()));
        }else {
            sb.append(varName);
        }
        sb.append(" = new ");
        sb.append(type.getShortNameWithoutTypeArguments());
        if(isGeneric) {
            sb.append("<>");
        }
        sb.append("();");
        return sb.toString();
    }
    

    /**
     * 增加表对应的实体的所有审计信息代码
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     * @param method
     */
    protected void addBaseRecordAllAudiInfoCode(Method method) {
        List<Field> fieldList = this.mixedContext.getBaseRecord().getFields();
        for(Field r:fieldList) {
            AudiFieldEnum audiField = AudiFieldEnum.resolve(r.getName());
            if(audiField==null) {
                continue;
            }
            switch (audiField) {
                case CREATE_DATE:
                case UPDATE_DATE:    
                    addBaseRecordAudiFieldCode(method,r,nowVarName);
                    break;
                case CREATOR:
                case MODIFIER:    
                    addBaseRecordAudiFieldCode(method,r,
                        StringUtils.join(userParameterName,".getName()"));
                    break;
                case CREATOR_ID:
                case MODIFIER_ID:    
                    addBaseRecordAudiFieldCode(method,r,
                        StringUtils.join(userParameterName,".getId()"));
                    break;
                    
                default:
                    break;
            }
       
        }
    }
    /**
     * 增加表对应的实体的审计字段信息代码
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     * @param mothod
     * @param field
     * @param varName
     */
    protected void addBaseRecordAudiFieldCode(Method mothod,Field field,String varName) {
        mothod.addBodyLine(
            StringUtils.join(
                baseRecordVarName,
                ".set",
                StringUtils.capitalize(field.getName()),
                "(",
                varName,
                ");"
                )
            );
    }
    
    
    /**
     * 增加表对应的实体的更新审计信息代码
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     * @param method
     */
    protected void addBaseRecordUpdateAudiInfoCode(Method method) {
        List<Field> fieldList = this.mixedContext.getBaseRecord().getFields();
        for(Field r:fieldList) {
            AudiFieldEnum audiField = AudiFieldEnum.resolve(r.getName());
            if(audiField==null) {
                continue;
            }
            switch (audiField) {
                case UPDATE_DATE:    
                    addBaseRecordAudiFieldCode(method,r,nowVarName);
                    break;
                case MODIFIER:    
                    addBaseRecordAudiFieldCode(method,r,
                        StringUtils.join(userParameterName,".getName()"));
                    break;
                case MODIFIER_ID:    
                    addBaseRecordAudiFieldCode(method,r,
                        StringUtils.join(userParameterName,".getId()"));
                    break;
                    
                default:
                    break;
            }
       
        }
    }
    
    /**
     * 获取Mapper字段名称
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-23 
     * @return
     */
    protected String getMapperFieldName() {
        return StringUtils.uncapitalize(this.mixedContext.getMapper().getType().getShortName());
    }

}
