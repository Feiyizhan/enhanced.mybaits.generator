/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.codegen;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.codegen.AbstractGenerator;

import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.MixedContext;

/**
 * 方法生成器基类
 * @author 徐明龙 XuMingLong 
 * @createDate 2018-11-20 
 */
public abstract class AbstractMethodGenerator extends AbstractGenerator {
    /**
     * 混合的上下文对象
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-20 
     */
    protected MixedContext mixedContext;
    
    public AbstractMethodGenerator(MixedContext mixedContext) {
        this.mixedContext = mixedContext;
    }
    
    /**
     * 增加方法
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-20 
     */
    public abstract void addMethod() ;
    
    /** 
     * 获取拼接的关键字列表Java字段名称
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-23 
     * @param joinStr
     * @param convertToCapitalize
     * @return
     */
    protected String getJoinedKeyColumnListJavaPropertyName(String joinStr,boolean convertToCapitalize) {
        List<String> nameList = getKeyColumnJavaPropertyNameList() ;
        if(convertToCapitalize) {
            nameList = nameList.stream()
                .map(StringUtils::capitalize)
                .collect(Collectors.toList());
        }
        return StringUtils.join(nameList, joinStr);
            
    }
    /**
     * 获取关键字的名称列表
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-23 
     * @return
     */
    protected List<String> getKeyColumnJavaPropertyNameList(){
        return this.introspectedTable.getPrimaryKeyColumns()
            .stream()
            .map(IntrospectedColumn::getJavaProperty)
            .collect(Collectors.toList());
    }

    
    /**
     * 获取用户类名参数
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-23 
     * @return
     */
    protected String getUserClassName() {
        return this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.USER_CLASS_NAME_KEY);
    }
    
    /**
     * 获取标准的校验和处理结果对象
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-23 
     * @return
     */
    protected String getStandardCheckAndHandleDtoClassName() {
        return this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.STANDARD_CHECK_AND_HANDLE_DTO_CLASS_NAME_KEY);
    }
    
    /**
     * 获取表单验证失败内容参数对象
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-23 
     * @return
     */
    protected String getFormValidErrorClassName() {
        return this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.FORM_VALID_ERROR_CLASS_NAME_KEY);
    }

    /**
     * 返回日期类型
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-23 
     * @return
     */
    public FullyQualifiedJavaType getNewLocalDateTimeType() {
        return new FullyQualifiedJavaType("java.time.LocalDateTime");
    }
}
