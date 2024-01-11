
package enhanced.mybaits.generator.codegen;

import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.MixedContext;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

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

    /**
     * 增加Lombok注解
     * @author 徐明龙 XuMingLong 2019-08-29
     * @param topLevelClass
     * @return void
     */
    protected void addLombokAnnotation(TopLevelClass topLevelClass) {
        topLevelClass.addImportedType("lombok.*");
        topLevelClass.addAnnotation("@Data");
    }


    /**
     * 增加Swagger2注解
     * @author 徐明龙 XuMingLong 2019-08-29
     * @param topLevelClass
     * @param extraComment
     * @return void
     */
    protected void addSwagger2Annotation(TopLevelClass topLevelClass,String extraComment) {
        topLevelClass.addImportedType("io.swagger.annotations.ApiModel");
        String tableComment = introspectedTable.getTableConfiguration().getProperties()
            .getProperty(EnhanceConstant.COMMENT_GENERATOR_TABLE_COMMENT_KEY);
        String remarks = introspectedTable.getRemarks();
        String comment = StringUtils.isNotBlank(remarks)?remarks:tableComment;
        topLevelClass.addAnnotation(String.join("","@ApiModel(\"",comment,extraComment,"\")"));
    }


    /**
     * 增加Spring Service注解
     * @author 徐明龙 XuMingLong
     * @param topLevelClass
     */
    protected void addSpringServiceAnnotation(TopLevelClass topLevelClass) {
        //增加Service注解
        topLevelClass.addAnnotation("@Service");
        //增加对应的引入
        topLevelClass.addImportedType("org.springframework.stereotype.Service");
    }

    /**
     * 增加Spring Repository注解
     * @author 徐明龙 XuMingLong
     * @param topLevelClass
     */
    protected void addSpringRepositoryAnnotation(TopLevelClass topLevelClass) {
        //增加Service注解
        topLevelClass.addAnnotation("@Repository");
        //增加对应的引入
        topLevelClass.addImportedType("org.springframework.stereotype.Repository");
    }


}
