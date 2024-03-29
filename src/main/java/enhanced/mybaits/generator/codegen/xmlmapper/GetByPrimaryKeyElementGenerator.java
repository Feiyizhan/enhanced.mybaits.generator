
package enhanced.mybaits.generator.codegen.xmlmapper;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import enhanced.mybaits.generator.enums.EnhanceSqlIdEnum;

/**
 * 增加获取指定主键对应的记录生成器
 * 
 * @author 徐明龙 XuMingLong
 */
public class GetByPrimaryKeyElementGenerator extends EnhanceAbstractXmlElementGenerator {

    public GetByPrimaryKeyElementGenerator() {
        super();
    }

    /**
     * 是否需要锁住记录
     * 
     * @author 徐明龙 XuMingLong
     */
    private boolean isLocked;

    public GetByPrimaryKeyElementGenerator(boolean isLocked) {
        super();
        this.isLocked = isLocked;
    }

    /**
     * 生成增加获取指定主键对应的记录节点
     * 
     * @author 徐明龙 XuMingLong
     * @param parentElement 父节点
     */
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        if (isLocked) {
            answer.addAttribute(new Attribute("id", EnhanceSqlIdEnum.GET_BY_PRIMARY_KEY_AND_LOCKED.getValue()));
        } else {
            answer.addAttribute(new Attribute("id", EnhanceSqlIdEnum.GET_BY_PRIMARY_KEY.getValue()));
        }
        answer.addAttribute(new Attribute("resultType", getBaseRecordName()));

        String parameterType;
        // PK fields are in the base class. If more than on PK
        // field, then they are coming in a map.
        if (introspectedTable.getPrimaryKeyColumns().size() > 1) {
            parameterType = "map";
        } else {
            parameterType =
                introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().toString();
        }

        answer.addAttribute(new Attribute("parameterType", parameterType));

        context.getCommentGenerator().addComment(answer);

        answer.addElement(new TextElement("select "));
        answer.addElement(getIncludeAllColumnListElement());
        answer.addElement(new TextElement("from "));
        answer.addElement(new TextElement("  " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));

        StringBuilder sb = new StringBuilder();

        boolean and = false;
        for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append("  and ");
            } else {
                sb.append("where ");
                and = true;
            }

            sb.append(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            answer.addElement(new TextElement(sb.toString()));
        }

        if (isLocked) {
            answer.addElement(new TextElement("for update"));
        }
        // TODO
        if (context.getPlugins().sqlMapSelectByPrimaryKeyElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }

    }

}
