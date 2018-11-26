/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。 Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be
 * used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.codegen.xmlmapper;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import enhanced.mybaits.generator.enums.EnhanceSqlIdEnum;

/**
 * 增强的Xml 节点生成器基础类
 * 
 * @author 徐明龙 XuMingLong
 */
public abstract class EnhanceAbstractXmlElementGenerator extends AbstractXmlElementGenerator {
    public EnhanceAbstractXmlElementGenerator() {
        super();
    }

    /**
     * 获取引入全字段列表节点
     * 
     * @author 徐明龙 XuMingLong
     * @return
     */
    protected XmlElement getIncludeAllColumnListElement() {
        XmlElement answer = new XmlElement("include");
        answer.addAttribute(new Attribute("refid", EnhanceSqlIdEnum.ALL_COLUMN_LIST.getValue()));
        return answer;
    }

    /**
     * 获取首字母小写的短名称
     * 
     * @author 徐明龙 XuMingLong
     * @param fullName
     * @return
     */
    protected String getUncapitalizeShortName(String fullName) {
        return StringUtils.uncapitalize(StringUtils.substringAfterLast(fullName, "."));
    }

    /**
     * 获取基础记录类型的首字母小写的短名称
     * 
     * @author 徐明龙 XuMingLong
     * @return
     */
    protected String getBaseRecordUncapitalizeShortName() {
        return getUncapitalizeShortName(this.introspectedTable.getBaseRecordType());
    }
}
