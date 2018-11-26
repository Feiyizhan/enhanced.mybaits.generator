/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。 Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be
 * used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.codegen.xmlmapper;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import enhanced.mybaits.generator.enums.EnhanceSqlIdEnum;

/**
 * 增强的删除指定主键的记录生成器
 * 
 * @author 徐明龙 XuMingLong
 */
public class EnhanceDeleteByPrimaryKeyElementGenerator extends EnhanceAbstractXmlElementGenerator {
    public EnhanceDeleteByPrimaryKeyElementGenerator() {
        super();
    }

    /**
     * 生成删除指定主键的记录节点
     * 
     * @author 徐明龙 XuMingLong
     * @param parentElement
     */
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("delete");

        answer.addAttribute(new Attribute("id", EnhanceSqlIdEnum.DELETE_BY_PRIMARY_KEY.getValue()));
        String parameterClass;
        // PK fields are in the base class. If more than on PK
        // field, then they are coming in a map.
        if (introspectedTable.getPrimaryKeyColumns().size() > 1) {
            parameterClass = "map";
        } else {
            parameterClass = getUncapitalizeShortName(
                introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().toString());
        }
        answer.addAttribute(new Attribute("parameterType", parameterClass));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("delete from ");
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        boolean and = false;
        for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append("  and ");
            } else {
                sb.append("where ");
                and = true;
            }

            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            answer.addElement(new TextElement(sb.toString()));
        }

        if (context.getPlugins().sqlMapDeleteByPrimaryKeyElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }

    }

}
