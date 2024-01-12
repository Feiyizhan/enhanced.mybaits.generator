package enhanced.mybaits.generator.codegen.domain.impl;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.domain.AbstractDomainImplMethodGenerator;
import enhanced.mybaits.generator.enums.DomainMethodEnum;
import org.mybatis.generator.api.dom.java.Method;

import java.util.List;

/**
 * Domain 接口实现类获取并锁定主键对应的记录方法生成器
 * @author 徐明龙 XuMingLong 
 */
public class DamainImplGetAndLockByPrimaryKeyMethodGenerator extends AbstractDomainImplMethodGenerator {

    public DamainImplGetAndLockByPrimaryKeyMethodGenerator(MixedContext mixedContext) {
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
        //增加返回结果
        sb.append("return ");
        sb.append(repositoryFieldName);
        sb.append(".");
        sb.append(calculateMethodName());
        sb.append("(");
        sb.append(getJoinedKeyColumnListJavaPropertyName(",",false));
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
        return null;
    }


    /**
     * 返回Domain方法枚举类
     * @author 徐明龙 XuMingLong 
     * @return 返回Domain方法枚举类
     */
    @Override
    protected DomainMethodEnum getDomainMethod() {
        return DomainMethodEnum.GET_AND_LOCK_BY_PRIMARY_KEY;
    }

    
    /**
     * 计算方法名称
     * @author 徐明龙 XuMingLong
     * @return 计算方法名称
     */
    @Override
    protected String calculateMethodName() {
        String subName = getJoinedKeyColumnListJavaPropertyName("And",true);
        return DomainMethodEnum.GET_AND_LOCK_BY_PRIMARY_KEY.getReplacePrimaryKeyValue(subName);
    }
}
