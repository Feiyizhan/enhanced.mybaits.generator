
package enhanced.mybaits.generator.codegen.xmlmapper;

import enhanced.mybaits.generator.enums.EnhanceSqlIdEnum;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.GeneratedKey;

import java.util.ArrayList;
import java.util.List;

/**
 * 增强的新增记录节点生成器
 * 
 * @author 徐明龙 XuMingLong
 */
public class EnhanceInsertElementGenerator extends EnhanceAbstractXmlElementGenerator {

    public EnhanceInsertElementGenerator() {
        super();
    }

    /**
     * 生成新增记录节点
     * 
     * @author 徐明龙 XuMingLong
     * @param parentElement 父节点
     */
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("insert");
        answer.addAttribute(new Attribute("id", EnhanceSqlIdEnum.INSERT.getValue()));

        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        answer.addAttribute(
            new Attribute("parameterType", parameterType.getFullyQualifiedName()));

        context.getCommentGenerator().addComment(answer);
        GeneratedKey gk = introspectedTable.getGeneratedKey();
        if (gk != null) {
            IntrospectedColumn introspectedColumn = introspectedTable.getColumn(gk.getColumn());
            if (introspectedColumn != null) {
                if (gk.isJdbcStandard()) {
                    answer.addAttribute(new Attribute("useGeneratedKeys", "true"));
                    answer.addAttribute(new Attribute("keyProperty", introspectedColumn.getJavaProperty()));
                    answer.addAttribute(new Attribute("keyColumn", introspectedColumn.getActualColumnName()));
                } else {
                    answer.addElement(getSelectKey(introspectedColumn, gk));
                }
            }
        }

        StringBuilder insertClause = new StringBuilder();
        insertClause.append("insert into ");
        insertClause.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" (");
        answer.addElement(new TextElement(insertClause.toString()));
        answer.addElement(getIncludeAllColumnListElement());
        StringBuilder valuesClause = new StringBuilder();
        List<String> valuesClauses = new ArrayList<String>();
        List<IntrospectedColumn> columns =
            ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns());
        for (int i = 0; i < columns.size(); i++) {
            IntrospectedColumn introspectedColumn = columns.get(i);
            valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            if (i + 1 < columns.size()) {
                valuesClause.append(", ");
            }
            valuesClauses.add(valuesClause.toString());
            valuesClause.setLength(0);
            OutputUtilities.xmlIndent(valuesClause, 1);
        }
        answer.addElement(new TextElement(")"));
        answer.addElement(new TextElement("values ("));
        valuesClause.append(')');
        valuesClauses.add(valuesClause.toString());

        for (String clause : valuesClauses) {
            answer.addElement(new TextElement(clause));
        }

        if (context.getPlugins().sqlMapInsertElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }

    }

}
