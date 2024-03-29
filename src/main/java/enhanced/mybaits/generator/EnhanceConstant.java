
package enhanced.mybaits.generator;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * MyBaits Generatior 增强处理常量
 * @author 徐明龙 XuMingLong 
 */
public interface EnhanceConstant {
    /**
     * 注释生成-作者
     * @author 徐明龙 XuMingLong 
     */
    String COMMENT_GENERATOR_AUTHOR_KEY = "author";

    /**
     * 注释生成-文件的注释内容
     * @author 徐明龙 XuMingLong 2019-08-29
     */
    String COMMENT_GENERATOR_FILE_COMMENT_KEY = "fileComment";

    /**
     * 注释生成-表的描述
     * @author 徐明龙 XuMingLong
     */
    String COMMENT_GENERATOR_TABLE_COMMENT_KEY ="table_comment";
    
    
    /**
     * SQL Mapper 生成-默认的主键生成SQL语句
     * @author 徐明龙 XuMingLong 
     */
    String SQL_MAPPER_GENERATOR_DEFAULT_KEY_SQL_STATEMENT= "JDBC";
    
    /**
     * 额外的Client测试类生成的目的包名参数
     * @author 徐明龙 XuMingLong 
     */
    String EXTRA_TEST_CLIENT_TARGET_PACKAGE_KEY="testClientTargetPackage";
    
    /**
     * 额外的测试类生成的目的目录参数
     * @author 徐明龙 XuMingLong 
     */
    String EXTRA_TEST_TARGET_PROJECT_KEY="testTargetProject";
    
    
    /**
     * 额外的测试类生成的Spring Boot 启动类名
     * @author 徐明龙 XuMingLong 
     */
    String EXTRA_TEST_SPRING_BOOT_MAIN_CLASS_KEY="testSpringBootMainClass";
    
    
    /**
     * 额外的Service接口生成的目的包名参数
     * @author 徐明龙 XuMingLong 
     */
    String EXTRA_SERVICE_TARGET_PACKAGE_KEY="serviceTargetPackage";
    
    /**
     * 额外的Service实现类生成的目的包名参数
     * @author 徐明龙 XuMingLong 
     */
    String EXTRA_SERVICE_IMPL_TARGET_PACKAGE_KEY="serviceImplTargetPackage";

    /**
     * 额外的Domain接口生成的目的包名参数
     * @author 徐明龙 XuMingLong
     */
    String EXTRA_DOMAIN_TARGET_PACKAGE_KEY="domainTargetPackage";

    /**
     * 额外的Domain实现类生成的目的包名参数
     * @author 徐明龙 XuMingLong
     */
    String EXTRA_DOMAIN_IMPL_TARGET_PACKAGE_KEY="domainImplTargetPackage";


    /**
     * 额外的Application接口生成的目的包名参数
     * @author 徐明龙 XuMingLong
     */
    String EXTRA_APPLICATION_TARGET_PACKAGE_KEY="applicationTargetPackage";

    /**
     * 额外的Application实现类生成的目的包名参数
     * @author 徐明龙 XuMingLong
     */
    String EXTRA_APPLICATION_IMPL_TARGET_PACKAGE_KEY="applicationImplTargetPackage";


    /**
     * 额外的API Service接口生成的目的包名参数
     * @author 徐明龙 XuMingLong
     */
    String EXTRA_API_SERVICE_TARGET_PACKAGE_KEY="apiServiceTargetPackage";

    /**
     * 额外的API Service实现类生成的目的包名参数
     * @author 徐明龙 XuMingLong
     */
    String EXTRA_API_SERVICE_IMPL_TARGET_PACKAGE_KEY="apiServiceImplTargetPackage";


    /**
     * 额外的Repository接口生成的目的包名参数
     * @author 徐明龙 XuMingLong
     */
    String EXTRA_REPOSITORY_TARGET_PACKAGE_KEY="repositoryTargetPackage";

    /**
     * 额外的Repository实现类生成的目的包名参数
     * @author 徐明龙 XuMingLong
     */
    String EXTRA_REPOSITORY_IMPL_TARGET_PACKAGE_KEY="repositoryImplTargetPackage";
    
    /**
     * 额外的Form类生成的目的包名参数
     * @author 徐明龙 XuMingLong 
     */
    String EXTRA_FORM_TARGET_PACKAGE_KEY="formTargetPackage";


    /**
     * 额外的Form类忽略的字段列表
     * @author 徐明龙 XuMingLong 
     */
    String EXTRA_FORM_IGNORE_FIELD_LIST_KEY="formIgnoreFieldList";
    
    /**
     * 额外的Res类生成的目的包名参数
     * @author 徐明龙 XuMingLong 
     */
    String EXTRA_RES_TARGET_PACKAGE_KEY ="resTargetPackage";

    /**
     * 额外的DO类生成的目的包名参数
     * @author 徐明龙 XuMingLong
     */
    String EXTRA_DO_TARGET_PACKAGE_KEY="dOTargetPackage";

    /**
     * 额外的DTO类生成的目的包名参数
     * @author 徐明龙 XuMingLong
     */
    String EXTRA_DTO_TARGET_PACKAGE_KEY="dTOTargetPackage";
    
    /**
     * 用户类名参数
     * @author 徐明龙 XuMingLong 
     */
    String USER_CLASS_NAME_KEY="userClassName";


    /**
     * id的类名参数
     * @author 徐明龙 XuMingLong
     */
    String ID_CLASS_NAME_KEY="idClassName";
    
    /**
     * 标准的校验和处理结果对象
     * @author 徐明龙 XuMingLong 
     */
    String STANDARD_CHECK_AND_HANDLE_DTO_CLASS_NAME_KEY = "standardCheckAndHandleDtoClassName";
    
    /**
     * 表单验证失败内容参数对象
     * @author 徐明龙 XuMingLong 
     */
    String FORM_VALID_ERROR_CLASS_NAME_KEY="formValidErrorClassName";
    
    /**
     * 获取当前日期静态方法
     * @author 徐明龙 XuMingLong 
     */
    String NOW_UTILS_KEY="nowUtils";



    /**
     * 时区id-东八区
     * @author 徐明龙 XuMingLong 
     */
    ZoneId  UTC_8 = ZoneId.of("UTC+8");
    
    /**
     * 日期格式器-年月日，例如：2018/04/18
     * @author 徐明龙 XuMingLong 
     */
    DateTimeFormatter FORMAT_DATE = new DateTimeFormatterBuilder()
            .appendPattern("yyyy/MM/dd")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
            .parseDefaulting(ChronoField.MICRO_OF_SECOND, 0)
            .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
            .toFormatter()
            .withZone(UTC_8);
}
