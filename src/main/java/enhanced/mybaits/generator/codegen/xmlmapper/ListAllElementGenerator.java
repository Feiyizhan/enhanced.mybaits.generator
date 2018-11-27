/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。 Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be
 * used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.codegen.xmlmapper;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

import enhanced.mybaits.generator.enums.EnhanceSqlIdEnum;

/**
 * 获取所有记录节点生成器
 * 
 * @author 徐明龙 XuMingLong
 */
public class ListAllElementGenerator extends EnhanceAbstractXmlElementGenerator {

    public ListAllElementGenerator() {
        super();
    }

    /**
     * 生成获取所有记录节点
     * 
     * @author 徐明龙 XuMingLong
     * @param parentElement 父节点
     */
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");

        answer.addAttribute(new Attribute("id", EnhanceSqlIdEnum.LIST_ALL.getValue()));

        answer.addAttribute(new Attribute("resultType", getBaseRecordUncapitalizeShortName()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        answer.addElement(new TextElement("select "));
        answer.addElement(getIncludeAllColumnListElement());
        answer.addElement(new TextElement("from "));
        answer.addElement(new TextElement("  " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));

        String orderByClause =
            introspectedTable.getTableConfigurationProperty(PropertyRegistry.TABLE_SELECT_ALL_ORDER_BY_CLAUSE);
        boolean hasOrderBy = StringUtility.stringHasValue(orderByClause);
        if (hasOrderBy) {
            sb.setLength(0);
            sb.append("order by ");
            sb.append(orderByClause);
            answer.addElement(new TextElement(sb.toString()));
        }

        // TODO
        if (context.getPlugins().sqlMapSelectAllElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }

    }

}
