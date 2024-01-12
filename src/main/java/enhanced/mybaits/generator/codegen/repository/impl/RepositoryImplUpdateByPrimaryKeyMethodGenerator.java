
package enhanced.mybaits.generator.codegen.repository.impl;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.enums.AudiFieldEnum;
import enhanced.mybaits.generator.enums.EnhanceSqlIdEnum;
import enhanced.mybaits.generator.enums.RepositoryMethodEnum;
import enhanced.mybaits.generator.codegen.repository.AbstractRepositoryImplMethodGenerator;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Repository 接口实现类更新指定记录方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class RepositoryImplUpdateByPrimaryKeyMethodGenerator extends AbstractRepositoryImplMethodGenerator {

    public RepositoryImplUpdateByPrimaryKeyMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 增加方法内容
     * @author 徐明龙 XuMingLong 
     * @param method 基于的方法
     */
    @Override
    protected void addMethodBody(Method method) {
        StringBuilder sb = new StringBuilder();
        sb.append("// 读取记录");
        method.addBodyLine(sb.toString());

        //生成定义关键字变量的代码
        List<IntrospectedColumn> keyColumnList = this.introspectedTable.getPrimaryKeyColumns();
        keyColumnList.forEach((r)->{
            FullyQualifiedJavaType parameterType = r.getFullyQualifiedJavaType();
            sb.setLength(0);
            sb.append(parameterType.getShortName());
            sb.append(" ");
            sb.append(StringUtils.uncapitalize(r.getJavaProperty()));
            sb.append(" = ");
            sb.append(dTOVarName);
            sb.append(".get");
            sb.append(StringUtils.capitalize(r.getJavaProperty()));
            sb.append("();");
            method.addBodyLine(sb.toString());
        });

        //增加读取记录代码
        FullyQualifiedJavaType baseRecordType = getBaseRecordType();
        sb.setLength(0);
        sb.append(baseRecordType.getShortNameWithoutTypeArguments());
        sb.append(" ");
        sb.append(baseRecordVarName);
        sb.append(" = ");
        sb.append(mapperFieldName);
        sb.append(".");
        sb.append(EnhanceSqlIdEnum.GET_BY_PRIMARY_KEY.getValue());
        sb.append("(");
        sb.append(getJoinedKeyColumnListJavaPropertyName(",",false));
        sb.append(");");
        method.addBodyLine(sb.toString());


        List<Field> baseFieldList = this.mixedContext.getBaseRecord().getFields();
        List<Field> dTOFieldList = this.mixedContext.getDTOClass().getFields();
        Map<String,Field> baseFieldMap = baseFieldList.stream()
            .collect(Collectors.toMap(Field::getName, Function.identity()));
        for(Field dtoField:dTOFieldList) {
            //审计代码最后生成
            AudiFieldEnum audiField = AudiFieldEnum.resolve(dtoField.getName());
            if(audiField!=null) {
                continue;
            }
            Field baseField = baseFieldMap.get(dtoField.getName());
            if(baseField!=null){
                method.addBodyLine(generateCopyFieldCode(baseField,dtoField,baseRecordVarName,dTOVarName));
            }
        }

        //增加更新操作的审计信息代码
        addBaseRecordUpdateAudiInfoCode(method);
        //增加更新语句
        sb.setLength(0);
        sb.append(mapperFieldName);
        sb.append(".");
        sb.append(EnhanceSqlIdEnum.UPDATE_BY_PRIMARY_KEY.getValue());
        sb.append("(");
        sb.append(baseRecordVarName);
        sb.append(");");
        method.addBodyLine(sb.toString());
        
        sb.setLength(0);
    }

    /**
     * 增加额外的方法
     * @author 徐明龙 XuMingLong 
     * @param method 基于的方法
     * @return 返回额外增加的方法
     */
    @Override
    protected List<Method> addExtraMethod(Method method) {
        List<Method> methodList = new ArrayList<>();
        return methodList;
    }


    /**
     * 返回Repository方法枚举类
     * @author 徐明龙 XuMingLong 
     * @return 返回Repository方法对应的枚举
     */
    @Override
    protected RepositoryMethodEnum getRepositoryMethod() {
        return RepositoryMethodEnum.UPDATE_BY_PRIMARY_KEY;
    }

    
    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong
     * @return 返回计算后的方法名
     */
    @Override
    protected String calculateMethodName() {
        String subName = getJoinedKeyColumnListJavaPropertyName("And",true);
        return RepositoryMethodEnum.UPDATE_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName);
    }
}
