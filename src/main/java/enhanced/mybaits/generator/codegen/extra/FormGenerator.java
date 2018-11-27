/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.codegen.extra;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstratEnhanceJavaGenerator;
import enhanced.mybaits.generator.codegen.IEnhanceCommentGenerator;
import enhanced.mybaits.generator.dom.java.FormClass;


/**
 * Form 类生成器
 * @author 徐明龙 XuMingLong 
 */
public class FormGenerator extends AbstratEnhanceJavaGenerator {

    public FormGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 设置Form生成位置
     * @author 徐明龙 XuMingLong 
     */
    @Override
    public void setTargetProject() {
        this.targetProject = calculateModelProject();
    }

    /**
     * 生成Form类
     * @author 徐明龙 XuMingLong 
     * @return Form类
     */
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        //生成Service 接口类
        FormClass formClass = getFormClass();
        answer.add(formClass);
        return answer;
    }

    /**
     * 生成Form类
     * @author 徐明龙 XuMingLong 
     * @return Form类
     */
    protected FormClass getFormClass() {
        progressCallback.startTask(String.format("准备生成表%s的Form类", introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
        }
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(calculateFormClassName());
        FormClass formClass = new FormClass(type);
        formClass.setVisibility(JavaVisibility.PUBLIC);
        mixedContext.setFormClass(formClass);
        //增加注释
        commentGenerator.addJavaFileComment(formClass);
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addFormClassComment(formClass, introspectedTable);
        }
        //增加字段
        TopLevelClass baseRecord = this.mixedContext.getBaseRecord();
        List<Field> fieldList = baseRecord.getFields();
        List<IntrospectedColumn> keyColumnList = this.introspectedTable.getPrimaryKeyColumns();
        List<IntrospectedColumn> allColumnList = this.introspectedTable.getAllColumns();
        String[] ignoreFieldList = getFormIgnoreFieldList();
        boolean hasField = false;
        for(Field r: fieldList) {
            boolean isIgnore = false;
            if(ignoreFieldList!=null && ignoreFieldList.length > 0) {
                for(String str:ignoreFieldList) {
                    if(r.getName().equals(str)) {
                        isIgnore = true;
                        break;
                    }
                }
            }
            if(isIgnore) {
                continue;
            }
            //跳过主键字段
            for(IntrospectedColumn key:keyColumnList) {
                if(r.getName().equals(key.getJavaProperty())) {
                    isIgnore = true;
                    break;
                }
            }
            if(isIgnore) {
                continue;
            }
            String remark = "";
            for(IntrospectedColumn column:allColumnList) {
                if(r.getName().equals(column.getJavaProperty())) {
                    remark = column.getRemarks();
                    break;
                }
            }
            Field newField = new Field(r);
            //增加Swagger注解
            newField.addAnnotation(String.join("", "@ApiModelProperty(value=\"",remark,"\")"));
            formClass.addField(newField);
            formClass.addImportedType(newField.getType());
            hasField = true;
 
        }
        //增加引入
        if(hasField) {
            formClass.addImportedType("io.swagger.annotations.ApiModelProperty");
        }
        //增加Lombok注解
        addLombokAnnotation(formClass);
        return formClass;
    }
    
    /**
     * 返回Form忽略的字段列表
     * @author 徐明龙 XuMingLong 
     * @return Form忽略的字段列表
     */
    protected String[] getFormIgnoreFieldList() {
        String value = this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.EXTRA_FORM_IGNORE_FIELD_LIST_KEY);
        if(StringUtils.isNotBlank(value)) {
            return StringUtils.split(value, ",");
        }else {
            return null;
        }
    }
    

    /**
     * 计算Form类名称
     * @author 徐明龙 XuMingLong 
     * @return Form类名称
     */
    protected String calculateFormClassName() {
        StringBuilder sb = new StringBuilder();
        sb.append(calculateFormClassPackage());
        sb.append('.');
        sb.append(this.mixedContext.getBaseRecord().getType().getShortName());
        sb.append("Form"); 
        return sb.toString();
    }
    
    /**
     * 计算Form类的Package
     * @author 徐明龙 XuMingLong 
     * @return Form类的Package
     */
    protected String calculateFormClassPackage() {
        String value = this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.EXTRA_FORM_TARGET_PACKAGE_KEY);
        if(!stringHasValue(value)) {
            return null;
        }else {
            return value;
        }
    }
    
}
