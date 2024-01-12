package enhanced.mybaits.generator.codegen.repository.impl;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.repository.AbstractRepositoryImplMethodGenerator;
import enhanced.mybaits.generator.enums.EnhanceSqlIdEnum;
import enhanced.mybaits.generator.enums.RepositoryMethodEnum;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Repository 接口实现类获取并锁定主键对应的记录方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class RepositoryImplGetAndLockByPrimaryKeyMethodGenerator extends AbstractRepositoryImplMethodGenerator {

    public RepositoryImplGetAndLockByPrimaryKeyMethodGenerator(MixedContext mixedContext) {
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

        sb.setLength(0);
        sb.append(getBaseRecordType().getShortNameWithoutTypeArguments());
        sb.append(" ");
        sb.append(baseRecordVarName);
        sb.append(" = ");
        sb.append(mapperFieldName);
        sb.append(".");
        sb.append(EnhanceSqlIdEnum.GET_BY_PRIMARY_KEY_AND_LOCKED.getValue());
        sb.append("(");
        sb.append(getJoinedKeyColumnListJavaPropertyName(",",false));
        sb.append(");");
        method.addBodyLine(sb.toString());

        FullyQualifiedJavaType dOType = this.mixedContext.getDOClass().getType();
        //增加 New DO对象的代码
        method.addBodyLine(getNewTypeCode(dOType,dOVarName));

        List<Field> baseFieldList = this.mixedContext.getBaseRecord().getFields();
        List<Field> dOFieldList = this.mixedContext.getDOClass().getFields();
        Map<String,Field> baseFieldMap = baseFieldList.stream()
            .collect(Collectors.toMap(Field::getName, Function.identity()));
        for(Field dOField:dOFieldList) {
            Field baseField = baseFieldMap.get(dOField.getName());
            if(baseField!=null){
                method.addBodyLine(generateCopyFieldCode(dOField,baseField,dOVarName,baseRecordVarName));
            }
        }
        //增加返回结果
        sb.setLength(0);
        sb.append("return ");
        sb.append(dOVarName);
        sb.append(";");
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
        return null;
    }


    /**
     * 返回Repository方法枚举类
     * @author 徐明龙 XuMingLong 
     * @return 返回Repository方法枚举类
     */
    @Override
    protected RepositoryMethodEnum getRepositoryMethod() {
        return RepositoryMethodEnum.GET_AND_LOCK_BY_PRIMARY_KEY;
    }

    
    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong
     * @return 计算方法名称
     */
    @Override
    protected String calculateMethodName() {
        String subName = getJoinedKeyColumnListJavaPropertyName("And",true);
        return RepositoryMethodEnum.GET_AND_LOCK_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName);
    }
}
