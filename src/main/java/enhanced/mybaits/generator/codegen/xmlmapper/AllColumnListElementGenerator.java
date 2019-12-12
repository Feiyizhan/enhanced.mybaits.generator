
package enhanced.mybaits.generator.codegen.xmlmapper;

import java.util.Iterator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import enhanced.mybaits.generator.enums.EnhanceSqlIdEnum;

/**
 * 全字段列表节点生成器
 * 
 * @author 徐明龙 XuMingLong
 */
public class AllColumnListElementGenerator extends EnhanceAbstractXmlElementGenerator {

    public AllColumnListElementGenerator() {
        super();
    }

    /**
     * 生成全部字段节点
     * 
     * @author 徐明龙 XuMingLong
     * @param parentElement 父节点
     */
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql");
        answer.addAttribute(new Attribute("id", EnhanceSqlIdEnum.ALL_COLUMN_LIST.getValue()));
        context.getCommentGenerator().addComment(answer);
        StringBuilder sb = new StringBuilder();
        Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns().iterator();
        while (iter.hasNext()) {
            sb.append(MyBatis3FormattingUtilities.getSelectListPhrase(iter.next()));

            if (iter.hasNext()) {
                sb.append(", ");
            }
            answer.addElement(new TextElement(sb.toString()));
            sb.setLength(0);
        }

        if (sb.length() > 0) {
            answer.addElement(new TextElement(sb.toString()));
        }
        
        // TODO 暂时没想到比较好的扩展 PluginAggregator 的方法的，因此复用了下BaseColumnList方法的 Plugin回调
        if (context.getPlugins().sqlMapBaseColumnListElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }

}
