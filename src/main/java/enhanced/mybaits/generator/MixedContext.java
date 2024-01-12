
package enhanced.mybaits.generator;

import enhanced.mybaits.generator.dom.java.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * 增强的上下文对象
 * @author 徐明龙 XuMingLong 
 */
@Getter@Setter@EqualsAndHashCode@ToString
public class MixedContext {

    /**
     * 生成的Mapper 接口
     * @author 徐明龙 XuMingLong 
     */
    private Interface mapper;
    /**
     * 基础数据类型
     * @author 徐明龙 XuMingLong 
     */
    private TopLevelClass baseRecord;
    
    /**
     * Mapper的测试类
     * @author 徐明龙 XuMingLong 
     */
    private TestsClass testsClass;
    
    /**
     * Service 接口类
     * @author 徐明龙 XuMingLong 
     */
    private ServiceInterface serviceInterface;

    /**
     * Service 实现类
     * @author 徐明龙 XuMingLong 
     */
    private ServiceImplClass serviceImplClass;

    /**
     * Repository 接口类
     * @author 徐明龙 XuMingLong
     */
    private RepositoryInterface repositoryInterface;

    /**
     * Repository 接口实现类
     * @author 徐明龙 XuMingLong
     */
    private RepositoryImplClass repositoryImplClass;


    /**
     * Domain 接口类
     * @author 徐明龙 XuMingLong
     */
    private DomainInterface domainInterface;

    /**
     * Domain 接口实现类
     * @author 徐明龙 XuMingLong
     */
    private DomainImplClass domainImplClass;

    /**
     * Application 接口类
     * @author 徐明龙 XuMingLong
     */
    private ApplicationInterface applicationInterface;

    /**
     * Application 接口实现类
     * @author 徐明龙 XuMingLong
     */
    private ApplicationImplClass applicationImplClass;


    /**
     * Form类
     * @author 徐明龙 XuMingLong 
     */
    private FormClass formClass;
    
    
    /**
     * Res类
     * @author 徐明龙 XuMingLong 
     */
    private ResClass resClass;


    /**
     * DO类
     * @author 徐明龙 XuMingLong
     */
    private DOClass dOClass;

    /**
     * DTO类
     * @author 徐明龙 XuMingLong
     */
    private DTOClass dTOClass;
    

}
