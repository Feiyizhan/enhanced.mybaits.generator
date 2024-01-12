
package enhanced.mybaits.generator.codegen.repository.impl;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.repository.AbstractRepositoryImplMethodGenerator;
import enhanced.mybaits.generator.enums.AudiFieldEnum;
import enhanced.mybaits.generator.enums.EnhanceSqlIdEnum;
import enhanced.mybaits.generator.enums.RepositoryMethodEnum;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Repository 接口实现类新增方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class RepositoryImplInsertMethodGenerator extends AbstractRepositoryImplMethodGenerator {

    public RepositoryImplInsertMethodGenerator(MixedContext mixedContext) {
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

        FullyQualifiedJavaType baseRecordType = getBaseRecordType();
        method.addBodyLine(getNewTypeCode(baseRecordType,baseRecordVarName));
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
        //增加审计信息代码
        addBaseRecordAllAudiInfoCode(method);
        //增加新增语句
        sb.setLength(0);
        sb.append(mapperFieldName);
        sb.append(".");
        sb.append(EnhanceSqlIdEnum.INSERT.getValue());
        sb.append("(");
        sb.append(baseRecordVarName);
        sb.append(");");
        method.addBodyLine(sb.toString());

        String autoIncrementKeyName = getAutoIncrementKeyName();
        if(autoIncrementKeyName!=null) {
            //增加返回结果
            sb.setLength(0);
            sb.append("return ");
            sb.append(baseRecordVarName);
            sb.append(".get");
            sb.append(StringUtils.capitalize(autoIncrementKeyName));
            sb.append("();");
            method.addBodyLine(sb.toString());
        }else{
            //增加返回结果
            sb.setLength(0);
            sb.append("return null;");
            method.addBodyLine(sb.toString());
        }

        sb.setLength(0);
    }

    /**
     * 增加额外的方法
     * @author 徐明龙 XuMingLong 
     * @param method 基于的方法
     * @return 增加的额外方法
     */
    @Override
    protected List<Method> addExtraMethod(Method method) {
        List<Method> methodList = new ArrayList<>();
        return methodList;
    }


    /**
     * 返回Repository方法枚举类
     * @author 徐明龙 XuMingLong 
     * @return 返回Repository方法枚举类
     */
    @Override
    protected RepositoryMethodEnum getRepositoryMethod() {
        return RepositoryMethodEnum.INSERT;
    }

    
    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong
     * @return 返回计算后的方法名
     */
    @Override
    protected String calculateMethodName() {
        return RepositoryMethodEnum.INSERT.getValue();
    }
}
