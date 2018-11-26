/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.codegen;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.dom.java.FormClass;
import enhanced.mybaits.generator.dom.java.ResultClass;
import enhanced.mybaits.generator.dom.java.ServiceImplClass;
import enhanced.mybaits.generator.dom.java.ServiceInterface;
import enhanced.mybaits.generator.dom.java.TestsClass;
import enhanced.mybaits.generator.enums.EnhanceSqlIdEnum;
import enhanced.mybaits.generator.enums.ServiceImplExtraMethodEnum;
import enhanced.mybaits.generator.enums.ServiceMethodEnum;

/**
 * My baits 生成器 注释增强处理
 * @author 徐明龙 XuMingLong 
 */
public class EnhanceCommentGenerator extends DefaultCommentGenerator implements IEnhanceCommentGenerator{
    
    
    private Properties properties;

    private boolean suppressDate;

    private boolean suppressAllComments;

    /** If suppressAllComments is true, this option is ignored. */
    private boolean addRemarkComments;

    private DateTimeFormatter dateFormat;
    
    private String author;
    
    public EnhanceCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = false;
    }

    
    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        suppressDate = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        
        suppressAllComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));

        addRemarkComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));
        
        String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
        if (StringUtility.stringHasValue(dateFormatString)) {
            dateFormat = new DateTimeFormatterBuilder()
                .appendPattern(dateFormatString)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
                .parseDefaulting(ChronoField.MICRO_OF_SECOND, 0)
                .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
                .toFormatter()
                .withZone(EnhanceConstant.UTC_8);
        }else {
            dateFormat = EnhanceConstant.FORMAT_DATE;
        }
        author = StringUtils.trimToEmpty(properties
            .getProperty(EnhanceConstant.COMMENT_GENERATOR_AUTHOR_KEY));
    }
    
    /**
     * 增加数据维护接口类的注释
     * @author 徐明龙 XuMingLong 
     * @param interfaze Mapper接口类
     * @param introspectedTable 对应的表 
     */
    @Override
    public void addMapperClassComment(Interface interfaze,IntrospectedTable introspectedTable) {
        addClassComment(interfaze,introspectedTable,"数据维护接口");
    }
    
    
    
    /** 
     * Java Mapper 测试类注释
     * @author 徐明龙 XuMingLong 
     * @param testsClass 测试类
     * @param introspectedTable 对应的表 
     */
    @Override
    public void addMapperTestsClassComment(TestsClass testsClass, IntrospectedTable introspectedTable) {
        addClassComment(testsClass,introspectedTable,"数据维护接口测试类");
    }


    /**
     * 文件级注释
     * @author 徐明龙 XuMingLong 
     * @param compilationUnit 代码类
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        compilationUnit.addFileCommentLine("/**");
        compilationUnit.addFileCommentLine(" * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。");
        compilationUnit.addFileCommentLine(" * Copyright©"+LocalDate.now().getYear()+"【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.");
        compilationUnit.addFileCommentLine("*/");
    }

    /**
     * Mapping.xml文件每个方法的的注释
     * @author 徐明龙 XuMingLong 
     * @param xmlElement xml节点
     */
    @Override
    public void addComment(XmlElement xmlElement) {
        if (suppressAllComments) {
            return;
        }
        xmlElement.addElement(new TextElement("<!--"));
        EnhanceSqlIdEnum sqlId = getSqlId(getElementIdValue(xmlElement));
        
        if(sqlId!=null) {
            xmlElement.addElement(new TextElement("   "+sqlId.getDescription()));
        }else {
            xmlElement.addElement(new TextElement("  "));
        }
        
        
        xmlElement.addElement(new TextElement("  @author "+this.author));
        xmlElement.addElement(new TextElement("-->"));
    }
    
    /**
     * 获取SQL Id
     * @author 徐明龙 XuMingLong 
     * @param value sqlId值
     * @return
     */
    private EnhanceSqlIdEnum getSqlId(String value) {
        EnhanceSqlIdEnum sqlId = EnhanceSqlIdEnum.resolve(value);
        if(sqlId==null) {
            sqlId = EnhanceSqlIdEnum.resolveByOldValue(value);
        }
        return sqlId;
    }
    
    /**
     * 获取指定Element的id属性值
     * @author 徐明龙 XuMingLong 
     * @param xmlElement xml节点
     * @return
     */
    private String getElementIdValue(XmlElement xmlElement) {
        return xmlElement.getAttributes()
            .stream()
            .filter(r->r.getName().equalsIgnoreCase("id"))
            .map(r->r.getValue()).findFirst().orElse(null);
    }


    
    /**
     * Mapping.xml文件根节点注释
     * @author 徐明龙 XuMingLong 
     * @param rootElement 根节点
     */
    @Override
    public void addRootComment(XmlElement rootElement) {
        if (suppressAllComments) {
            return;
        }
        rootElement.addElement(new TextElement("<!--")); 
        rootElement.addElement(new TextElement("  版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。 ")); 
        rootElement.addElement(new TextElement("  Copyright©"+LocalDate.now().getYear()+"【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent. ")); 
        rootElement.addElement(new TextElement("-->")); 
        
    }



    /**
     * 实体类的注释
     * @author 徐明龙 XuMingLong 
     * @param topLevelClass 实体类
     * @param introspectedTable 对应的表 
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments  || !addRemarkComments) {
            return;
        }
        topLevelClass.addJavaDocLine("/**"); 
        String tableComment = introspectedTable.getTableConfiguration().getProperties()
            .getProperty(EnhanceConstant.COMMENT_GENERATOR_TABLE_COMMENT_KEY);
        String remarks = introspectedTable.getRemarks();
        if (addRemarkComments) {
            String tmp = null;
            if(StringUtils.isNotBlank(remarks)) {
                tmp = remarks;
            }else if(StringUtils.isNotBlank(tableComment)) {
                tmp = tableComment;
            }
            if(StringUtils.isNotBlank(tmp)) {
                topLevelClass.addJavaDocLine(" * 表的描述:"); 
                String[] remarkLines = tmp.split(System.getProperty("line.separator"));  
                for (String remarkLine : remarkLines) {
                    topLevelClass.addJavaDocLine(" *   " + remarkLine);  
                }
            }
            
        }
        topLevelClass.addJavaDocLine(" *"); 
        StringBuilder sb = new StringBuilder();
        sb.append(" * 对应的表名: "); 
        sb.append(introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(sb.toString());
        // 增加创建人
        addAuthorJavadocTag(topLevelClass);
        topLevelClass.addJavaDocLine(" */"); 
        //增加Lombok注释
        addLombokAnnotation(topLevelClass);
    }
    
    /**
     * 增加Lombok注解
     * @author 徐明龙 XuMingLong 
     * @param topLevelClass 实体类
     */
    protected void addLombokAnnotation(TopLevelClass topLevelClass) {
        topLevelClass.addImportedType("lombok.EqualsAndHashCode"); 
        topLevelClass.addImportedType("lombok.Getter"); 
        topLevelClass.addImportedType("lombok.Setter"); 
        topLevelClass.addImportedType("lombok.ToString"); 
        
        topLevelClass.addAnnotation("@Getter");
        topLevelClass.addAnnotation("@Setter");
        topLevelClass.addAnnotation("@EqualsAndHashCode");
        topLevelClass.addAnnotation("@ToString");
        
    }
    
    
    /**
     * 增加创建人Java Doc标签
     * @author 徐明龙 XuMingLong 
     * @param javaElement Java类的节点
     */
    protected void addAuthorJavadocTag(JavaElement javaElement) {
        StringBuilder sb = new StringBuilder();
        sb.append(" * "); 
        sb.append("@author");
        if (author != null) {
            sb.append(' ');
            sb.append(author);
            sb.append(' ');
            sb.append(getDateString());
        }
        javaElement.addJavaDocLine(sb.toString());
        
    }

    
    /**
     * 获取日期字符串
     * @author 徐明龙 XuMingLong 
     * @return 返回格式化好的日期字符串
     */
    protected String getDateString() {
        if (suppressDate) {
            return null;
        } else if (dateFormat != null) {
            return dateFormat.format(Instant.now());
        } else {
            return Instant.now().toString();
        }
    }

    /**
     * model类增加字段的注释
     * @author 徐明龙 XuMingLong 
     * @param field 字段
     * @param introspectedTable 对应的表 
     * @param introspectedColumn 对应的列名
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
        IntrospectedColumn introspectedColumn) {
        field.addJavaDocLine("/**"); 
        field.addJavaDocLine(" * "+introspectedColumn.getRemarks()); 
        // 增加创建人
        addAuthorJavadocTag(field);
        field.addJavaDocLine(" */"); 
    }

    /**
     * 增加方法的注释
     * @author 徐明龙 XuMingLong 
     * @param method 方法类
     * @param introspectedTable 对应的表 
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        
        EnhanceSqlIdEnum sqlId = getSqlId(method.getName());
        String comment = "";
        if(sqlId!=null) {
            comment = sqlId.getDescription();
        }else {
            comment="";
        }
        addMethodComment(method, comment);
    }
    
    

    /**
     * Java Mapper 测试类方法注释
     * @author 徐明龙 XuMingLong 
     * @param method  方法类
     */
    @Override
    public void addMapperTestsMethodComment(Method method) {
        EnhanceSqlIdEnum sqlId = getSqlId(method.getName());
        String comment = "";
        if(sqlId!=null) {
            comment = String.join("", sqlId.getDescription(),"测试方法");
        }else {
            comment="";
        }
        addMethodComment(method, comment);
    }


    


    /**
     * Service接口类注释
     * @author 徐明龙 XuMingLong 
     * @param serviceInterface Service接口类
     * @param introspectedTable 对应的表 
     */
    @Override
    public void addServiceInterfaceComment(ServiceInterface serviceInterface, IntrospectedTable introspectedTable) {
        addClassComment(serviceInterface,introspectedTable,"Service接口");
    }


    /**
     * Form类注释
     * @author 徐明龙 XuMingLong 
     * @param formClass 表单类
     * @param introspectedTable 对应的表 
     */
    @Override
    public void addFormClassComment(FormClass formClass, IntrospectedTable introspectedTable) {
        addClassComment(formClass,introspectedTable,"表单");
    }


    /**
     * Result类注释
     * @author 徐明龙 XuMingLong 
     * @param resultClass 返回结果类
     * @param introspectedTable 对应的表 
     */
    @Override
    public void addResultClassComment(ResultClass resultClass, IntrospectedTable introspectedTable) {
        addClassComment(resultClass,introspectedTable,"返回结果");
    }


    /**
     * Service接口实现类
     * @author 徐明龙 XuMingLong 
     * @param serviceImplClass Service实现类
     * @param introspectedTable 对应的表 
     */
    @Override
    public void addServiceImplClassComment(ServiceImplClass serviceImplClass, IntrospectedTable introspectedTable) {
        addClassComment(serviceImplClass,introspectedTable,"Service接口的实现类");
    }
    


    /**
     * 增加注入的Mapper字段的注释
     * @author 徐明龙 XuMingLong 
     * @param field 注入的字段类
     * @param introspectedTable 对应的表 
     */
    @Override
    public void addAutowiredMapperFieldComment(Field field, IntrospectedTable introspectedTable) {
        addClassComment(field,introspectedTable,"数据维护接口");
    }
    
    /**
     * Service 方法的注释
     * @author 徐明龙 XuMingLong 
     * @param method 方法类
     * @param methodEnum 方法对应的枚举
     */
    @Override
    public void addServiceMethodComment(Method method,ServiceMethodEnum methodEnum) {
        String description = "";
        if(methodEnum!=null) {
            description = methodEnum.getDescription();
        }
        addMethodComment(method,description);
    }
    

    
    /**
     * Service 扩展的方法的注释
     * @author 徐明龙 XuMingLong 
     * @param method 方法类
     */
    @Override
    public void addServiceExtraMethodComment(Method method) {
        ServiceImplExtraMethodEnum methodEnum = ServiceImplExtraMethodEnum.resolve(method.getName());
        String description = "";
        if(methodEnum!=null) {
            description = methodEnum.getDescription();
        }
        addMethodComment(method,description);
        
        
    }

    /**
     * 增加方法的注释
     * @author 徐明龙 XuMingLong 
     * @param method
     * @param comment
     */
    private void addMethodComment(Method method,String comment) {
        method.addJavaDocLine("/**"); 
        method.addJavaDocLine(String.join("", " * ",comment));
        // 增加创建人
        addAuthorJavadocTag(method);
        //增加参数
        method.getParameters().forEach((r)->{
            method.addJavaDocLine(String.join("", " * ","@param ",r.getName()));
        });
        method.addJavaDocLine(" */"); 
    }
    
    /** 
     *  增加Class的注释
     * @author 徐明龙 XuMingLong 
     * @param javaElement
     * @param introspectedTable
     */
    private void addClassComment(JavaElement javaElement,IntrospectedTable introspectedTable,String extraComment) {
        javaElement.addJavaDocLine("/**"); 
        String tableComment = introspectedTable.getTableConfiguration().getProperties()
            .getProperty(EnhanceConstant.COMMENT_GENERATOR_TABLE_COMMENT_KEY);
        String remarks = introspectedTable.getRemarks();
        String comment = StringUtils.isNotBlank(remarks)?remarks:tableComment;
        javaElement.addJavaDocLine(String.join("", " * ",comment,extraComment));
        // 增加创建人
        addAuthorJavadocTag(javaElement);
        javaElement.addJavaDocLine(" */"); 
        
    }


}
