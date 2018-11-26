/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.codegen;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.MixedContext;

/**
 * 增强的Java生成器抽象类
 * @author 徐明龙 XuMingLong 
 */
public abstract class AbstratEnhanceJavaGenerator extends AbstractJavaGenerator{

    /**
     * 混合的上下文对象
     * @author 徐明龙 XuMingLong 
     */
    protected MixedContext mixedContext;
    
    /**
     * 生成的目标项目目录
     * @author 徐明龙 XuMingLong 
     */
    protected String targetProject;

    
    public AbstratEnhanceJavaGenerator(MixedContext mixedContext) {
        this.mixedContext = mixedContext;
    }
    
    /**
     * 获取生成的目标项目目录
     * @author 徐明龙 XuMingLong 
     * @return 生成的目标项目目录
     */
    public String getTargetProject() {
        return targetProject;
    }
    
    /**
     * 设置生成的目标项目目录
     * @author 徐明龙 XuMingLong 
     */
    public abstract void setTargetProject() ;
    
    
    /**
     * 获取测试项目路径
     * @author 徐明龙 XuMingLong 
     * @return 测试项目路径
     */
    protected String calculateTestProject() {
        String testProject = this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.EXTRA_TEST_TARGET_PROJECT_KEY);
        if(!stringHasValue(testProject)) {
            return null;
        }else {
            return testProject;
        }
    }
    
    /**
     * 获取Java Client项目路径
     * @author 徐明龙 XuMingLong 
     * @return Java Client项目路径
     */
    protected String calculateJavaClientProject() {
        return this.context.getJavaClientGeneratorConfiguration().getTargetProject();
    }
    
    /**
     * 获取Java Model项目路径
     * @author 徐明龙 XuMingLong 
     * @return Java Model项目路径
     */
    protected String calculateModelProject() {
        return this.context.getJavaModelGeneratorConfiguration().getTargetProject();
    }
    
    /**
     * 返回日期类型
     * @author 徐明龙 XuMingLong 
     * @return 返回LocalDateTime的类型对象
     */
    public FullyQualifiedJavaType getNewLocalDateTimeType() {
        return new FullyQualifiedJavaType("java.time.LocalDateTime");
    }
       
    
    /**
     * 获取取当前日期静态方法名
     * @author 徐明龙 XuMingLong 
     * @return 返回获取当前日期的静态方法名
     */
    protected String getNowUtilsName() {
        return this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.NOW_UTILS_KEY);
    }
    
}
