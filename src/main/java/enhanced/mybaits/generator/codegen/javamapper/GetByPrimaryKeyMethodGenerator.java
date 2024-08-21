
package enhanced.mybaits.generator.codegen.javamapper;

import enhanced.mybaits.generator.codegen.AbstractEnhanceJavaMapperMethodGenerator;
import enhanced.mybaits.generator.enums.EnhanceSqlIdEnum;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 获取指定主键对应的记录方法生成器
 * 
 * @author 徐明龙 XuMingLong
 */
public class GetByPrimaryKeyMethodGenerator extends AbstractEnhanceJavaMapperMethodGenerator {
    
    
    /**
     * 是否锁定记录
     * 
     * @author 徐明龙 XuMingLong
     */
    private boolean isLocked;

    public GetByPrimaryKeyMethodGenerator(boolean isLocked) {
        super();
        this.isLocked = isLocked;
    }

    /**
     * 生成获取指定主键对应的记录方法
     * 
     * @author 徐明龙 XuMingLong
     * @param interfaze 接口类
     */
    @Override
    public void addInterfaceElements(Interface interfaze) {

        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = isLocked?new Method(EnhanceSqlIdEnum.GET_BY_PRIMARY_KEY_AND_LOCKED.getValue()):
            new Method(EnhanceSqlIdEnum.GET_BY_PRIMARY_KEY.getValue());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(true);
        FullyQualifiedJavaType returnType = introspectedTable.getRules().calculateAllFieldsClass();
        method.setReturnType(returnType);
        importedTypes.add(returnType);



        if (introspectedTable.getRules().generatePrimaryKeyClass()) {
            FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
            importedTypes.add(type);
            method.addParameter(new Parameter(type, "key"));
        } else {
            // no primary key class - fields are in the base class
            // if more than one PK field, then we need to annotate the
            // parameters
            // for MyBatis3
            List<IntrospectedColumn> introspectedColumns = introspectedTable.getPrimaryKeyColumns();
            boolean annotate = introspectedColumns.size() > 1;
            if (annotate) {
                importedTypes.add(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
            }
            StringBuilder sb = new StringBuilder();
            for (IntrospectedColumn introspectedColumn : introspectedColumns) {
                FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
                importedTypes.add(type);
                Parameter parameter = new Parameter(type, introspectedColumn.getJavaProperty());
                if (annotate) {
                    sb.setLength(0);
                    sb.append("@Param(\"");
                    sb.append(introspectedColumn.getJavaProperty());
                    sb.append("\")");
                    parameter.addAnnotation(sb.toString());
                }
                method.addParameter(parameter);
            }
        }

        addMapperAnnotations(interfaze, method);

        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

        if (context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable)) {
            addExtraImports(interfaze);
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

   

}
