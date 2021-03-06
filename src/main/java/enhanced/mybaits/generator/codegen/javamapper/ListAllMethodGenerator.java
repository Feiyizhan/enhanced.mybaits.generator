
package enhanced.mybaits.generator.codegen.javamapper;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;

import enhanced.mybaits.generator.codegen.AbstractEnhanceJavaMapperMethodGenerator;
import enhanced.mybaits.generator.enums.EnhanceSqlIdEnum;

/**
 * 获取所有记录方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class ListAllMethodGenerator extends AbstractEnhanceJavaMapperMethodGenerator {

    
    public ListAllMethodGenerator() {
        super();
    }

    /**
     * 生成获取所有记录方法
     * @author 徐明龙 XuMingLong 
     * @param interfaze 接口类
     */
    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = FullyQualifiedJavaType
                .getNewListInstance();
        FullyQualifiedJavaType listType;
        listType = new FullyQualifiedJavaType(
                introspectedTable.getBaseRecordType());

        importedTypes.add(listType);
        returnType.addTypeArgument(listType);
        method.setReturnType(returnType);
        method.setName(EnhanceSqlIdEnum.LIST_ALL.getValue());

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        addMapperAnnotations(interfaze, method);

        if (context.getPlugins().clientSelectAllMethodGenerated(method,
                interfaze, introspectedTable)) {
            addExtraImports(interfaze);
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

}
