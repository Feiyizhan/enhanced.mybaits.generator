
package enhanced.mybaits.generator.codegen.service.impl;


import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.service.AbstractServiceImplMethodGenerator;
import enhanced.mybaits.generator.enums.EnhanceSqlIdEnum;
import enhanced.mybaits.generator.enums.ServiceMethodEnum;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;

import java.util.List;

/**
 * Service 接口实现类获取主键对应的记录的返回结果对象方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class ServiceImplGetResByPrimaryKeyMethodGenerator extends AbstractServiceImplMethodGenerator {

    public ServiceImplGetResByPrimaryKeyMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 增加方法内容
     * @author 徐明龙 XuMingLong 
     * @param method 基于的方法
     */
    @Override
    protected void addMethodBody(Method method) {
        FullyQualifiedJavaType returnType = method.getReturnType();
        method.addBodyLine(getNewTypeCode(returnType,returnVarName));
        
        StringBuilder sb = new StringBuilder();
        
        //增加获取数据语句
        sb.setLength(0);
        sb.append(getBaseRecordType().getShortNameWithoutTypeArguments());
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
        
        sb.setLength(0);
        sb.append("BeanUtils.copyProperties(");
        sb.append(baseRecordVarName);
        sb.append(",");
        sb.append(returnVarName);
        sb.append(");");
        method.addBodyLine(sb.toString());
        
        //增加返回结果
        sb.setLength(0);
        sb.append("return ");
        sb.append(returnVarName);
        sb.append(";");
        method.addBodyLine(sb.toString());
        sb.setLength(0);
    }

    /**
     * 增加额外的方法
     * @author 徐明龙 XuMingLong 
     * @param method 基于的方法
     * @return 增加的额外的方法
     */
    @Override
    protected List<Method> addExtraMethod(Method method) {
        return null;
    }


    /**
     * 返回Service方法枚举类
     * @author 徐明龙 XuMingLong 
     * @return 返回Service方法枚举类
     */
    @Override
    protected ServiceMethodEnum getServiceMethod() {
        return ServiceMethodEnum.GET_RESULT_BY_PRIMARY_KEY;
    }

    
    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong
     * @return 计算方法名称
     */
    @Override
    protected String calculateMethodName() {
        String subName = getJoinedKeyColumnListJavaPropertyName("And",true);
        return ServiceMethodEnum.GET_RESULT_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName);
    }
}
