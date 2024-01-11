
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
     * @return 返回引入全字段列表节点
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
     * @param fullName 全字段名称可以包含包名
     * @return 获取首字母小写的短名称
     */
    protected String getUncapitalizeShortName(String fullName) {
        return StringUtils.uncapitalize(StringUtils.substringAfterLast(fullName, "."));
    }

    /**
     * 获取基础记录类型的首字母小写的短名称
     * 
     * @author 徐明龙 XuMingLong
     * @return  基础记录类型的首字母小写的短名称
     */
    protected String getBaseRecordUnCapitalizeShortName() {
        return getUncapitalizeShortName(this.introspectedTable.getBaseRecordType());
    }

    /**
     * 获取基础记录类型的首字母小写的短名称
     *
     * @author 徐明龙 XuMingLong
     * @return  基础记录类型的首字母小写的短名称
     */
    protected String getBaseRecordName() {
        return this.introspectedTable.getBaseRecordType();
    }
}
