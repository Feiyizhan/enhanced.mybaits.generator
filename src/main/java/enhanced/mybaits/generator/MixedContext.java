/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator;

import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import enhanced.mybaits.generator.dom.java.FormClass;
import enhanced.mybaits.generator.dom.java.ResultClass;
import enhanced.mybaits.generator.dom.java.ServiceImplClass;
import enhanced.mybaits.generator.dom.java.ServiceInterface;
import enhanced.mybaits.generator.dom.java.TestsClass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 增强的上下文对象
 * @author 徐明龙 XuMingLong 
 * @createDate 2018-11-19 
 */
@Getter@Setter@EqualsAndHashCode@ToString
public class MixedContext {

    /**
     * 生成的Mapper 接口
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     */
    private Interface mapper;
    /**
     * 基础数据类型
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     */
    private TopLevelClass baseRecord;
    
    /**
     * Mapper的测试类
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-19 
     */
    private TestsClass testsClass;
    
    /**
     * Service 接口类
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-20 
     */
    private ServiceInterface serviceInterface;
    
    /**
     * Service 实现类
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-20 
     */
    private ServiceImplClass serviceImplClass;
    
    /**
     * Form类
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-20 
     */
    private FormClass formClass;
    
    
    /**
     * Result类
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-20 
     */
    private ResultClass resultClass;
    
    

}
