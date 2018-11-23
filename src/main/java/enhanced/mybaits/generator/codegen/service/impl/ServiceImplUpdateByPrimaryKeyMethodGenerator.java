/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.codegen.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstractServiceImplMethodGenerator;
import enhanced.mybaits.generator.codegen.IEnhanceCommentGenerator;
import enhanced.mybaits.generator.enums.EnhanceSqlIdEnum;
import enhanced.mybaits.generator.enums.ServiceImplExtraMethodEnum;
import enhanced.mybaits.generator.enums.ServiceMethodEnum;

/**
 * Service 接口实现类更新指定记录方法生成器
 * @author 徐明龙 XuMingLong 
 * @createDate 2018-11-21 
 */
public class ServiceImplUpdateByPrimaryKeyMethodGenerator extends AbstractServiceImplMethodGenerator {

    public ServiceImplUpdateByPrimaryKeyMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 增加方法内容
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     * @param method
     */
    @Override
    protected void addMethodBody(Method method) {
        FullyQualifiedJavaType listFormValidErrorType = getListFormValidErrorType();
        
        
        StringBuilder sb = new StringBuilder();
        sb.append("// 读取并锁定记录");
        method.addBodyLine(sb.toString());
        //增加读取并锁定记录代码
        FullyQualifiedJavaType baseRecordType = getBaseRecordType();
        sb.setLength(0);
        sb.append(baseRecordType.getShortNameWithoutTypeArguments());
        sb.append(" ");
        sb.append(baseRecordVarName);
        sb.append(" = ");
        sb.append(mapperFieldName);
        sb.append(".");
        sb.append(EnhanceSqlIdEnum.GET_BY_PRIMARY_KEY_AND_LOCKED.getValue());
        sb.append("(");
        sb.append(getJoinedKeyColumnListJavaPropertyName(",",false));
        sb.append(");");
        method.addBodyLine(sb.toString());

        
        sb.setLength(0);
        sb.append("// 修改表单校验");
        method.addBodyLine(sb.toString());
        
        //增加调用编辑表单校验的代码
        sb.setLength(0);
        sb.append(listFormValidErrorType.getShortName());
        sb.append(" ");
        sb.append(errorVarName);
        sb.append(" = ");
        sb.append(ServiceImplExtraMethodEnum.VERIFY_FORM_FOR_UPDATE.getValue());
        sb.append("(");
        sb.append(baseRecordVarName);
        sb.append(",");
        sb.append(formParameterName);
        sb.append(");");
        method.addBodyLine(sb.toString());
        
        sb.setLength(0);
        sb.append("if (CollectionUtils.isEmpty(");
        sb.append(errorVarName);
        sb.append(")) {");
        method.addBodyLine(sb.toString());
        
        sb.setLength(0);
        sb.append("LocalDateTime ");
        sb.append(nowVarName);
        sb.append(" = now();");
        method.addBodyLine(sb.toString());
        
        sb.setLength(0);
        sb.append("BeanUtils.copyProperties(");
        sb.append(formParameterName);
        sb.append(",");
        sb.append(baseRecordVarName);
        sb.append(");");
        method.addBodyLine(sb.toString());
        
        //增加更新操作的审计信息代码
        addBaseRecordUpdateAudiInfoCode(method);
        //增加更新语句
        sb.setLength(0);
        sb.append(mapperFieldName);
        sb.append(".");
        sb.append(EnhanceSqlIdEnum.UPDATE_BY_PRIMARY_KEY.getValue());
        sb.append("(");
        sb.append(baseRecordVarName);
        sb.append(");");
        method.addBodyLine(sb.toString());
        
        method.addBodyLine("}");
        //增加返回结果
        sb.setLength(0);
        sb.append("return ");
        sb.append(errorVarName);
        sb.append(";");
        method.addBodyLine(sb.toString());
        sb.setLength(0);
    }

    /**
     * 增加额外的方法
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     * @param method
     * @return
     */
    @Override
    protected List<Method> addExtraMethod(Method method) {
        List<Method> methodList = new ArrayList<>();
        Method verifyFromForUpdateMethod = new Method();
        verifyFromForUpdateMethod.setName(ServiceImplExtraMethodEnum.VERIFY_FORM_FOR_UPDATE.getValue());
        
        FullyQualifiedJavaType baseRecordType = getBaseRecordType();
        Parameter baseRecordParameter = new Parameter(baseRecordType,
            baseRecordVarName);
        verifyFromForUpdateMethod.addParameter(baseRecordParameter);
        
        FullyQualifiedJavaType formType = this.mixedContext.getFormClass().getType();
        Parameter formParameter = new Parameter(formType,
            formParameterName);
        verifyFromForUpdateMethod.addParameter(formParameter);
        
        verifyFromForUpdateMethod.setVisibility(JavaVisibility.PRIVATE);
        
        FullyQualifiedJavaType returnType = getListFormValidErrorType();
        //增加返回参数
        verifyFromForUpdateMethod.setReturnType(returnType);
        StringBuilder sb = new StringBuilder();
        sb.append(returnType.getShortName());
        sb.append(" ");
        sb.append(errorVarName);
        FullyQualifiedJavaType arrayListType = FullyQualifiedJavaType.getNewArrayListInstance();
        sb.append(" = new ");
        sb.append(arrayListType.getShortNameWithoutTypeArguments());
        sb.append("<>();");
        verifyFromForUpdateMethod.addBodyLine(sb.toString());
        verifyFromForUpdateMethod.addBodyLine("// TODO 补充表单校验内容");
        sb.setLength(0);
        sb.append("return ");
        sb.append(errorVarName);
        sb.append(";");
        verifyFromForUpdateMethod.addBodyLine(sb.toString());
        
        
        //生成注释
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        CommentGenerator commentGenerator = context.getCommentGenerator();
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
            enhanceCommentGenerator.addServiceExtraMethodComment(verifyFromForUpdateMethod);
        }
        
        methodList.add(verifyFromForUpdateMethod);
        return methodList;
    }


    /**
     * 返回Service方法枚举类
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-23 
     * @return
     */
    @Override
    protected ServiceMethodEnum getServiceMethod() {
        return ServiceMethodEnum.UPDATE_BY_PRIMARY_KEY;
    }

    
    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong
     * @createDate 2018-11-23
     * @return
     */
    @Override
    protected String calculateMethodName() {
        String subName = getJoinedKeyColumnListJavaPropertyName("And",true);
        return ServiceMethodEnum.UPDATE_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName);
    }
}
