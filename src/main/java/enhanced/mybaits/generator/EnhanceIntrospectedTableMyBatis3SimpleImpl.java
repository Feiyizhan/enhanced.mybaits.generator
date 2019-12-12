
package enhanced.mybaits.generator;


import java.util.List;

import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3SimpleImpl;
import org.mybatis.generator.codegen.mybatis3.javamapper.SimpleAnnotatedClientGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.SimpleJavaClientGenerator;
import org.mybatis.generator.internal.ObjectFactory;

import enhanced.mybaits.generator.codegen.EnhanceSimpleJavaClientGenerator;
import enhanced.mybaits.generator.codegen.EnhanceSimpleXMLMapperGenerator;

/**
 * My baits 生成器 代码生成类
 * 
 * @author 徐明龙 XuMingLong
 */
public class EnhanceIntrospectedTableMyBatis3SimpleImpl extends IntrospectedTableMyBatis3SimpleImpl {

    /**
     * 选择XML Mapper生成器
     * 
     * @author 徐明龙 XuMingLong
     * @param javaClientGenerator 对应的JavaClient生成器
     * @param warnings 额外的参数
     * @param progressCallback 处理进展通知回调类
     */
    @Override
    protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator, List<String> warnings,
        ProgressCallback progressCallback) {
        if (javaClientGenerator == null) {
            if (context.getSqlMapGeneratorConfiguration() != null) {
                xmlMapperGenerator = new EnhanceSimpleXMLMapperGenerator();
            }
        } else {
            xmlMapperGenerator = javaClientGenerator.getMatchedXMLGenerator();
        }

        initializeAbstractGenerator(xmlMapperGenerator, warnings, progressCallback);

        super.calculateXmlMapperGenerator(javaClientGenerator, warnings, progressCallback);
    }

    /**
     * 选择Java Client生成器
     * 
     * @author 徐明龙 XuMingLong
     * @return Java Client生成器
     */
    @Override
    protected AbstractJavaClientGenerator createJavaClientGenerator() {
        if (context.getJavaClientGeneratorConfiguration() == null) {
            return null;
        }

        String type = context.getJavaClientGeneratorConfiguration().getConfigurationType();

        AbstractJavaClientGenerator javaGenerator;
        if ("XMLMAPPER".equalsIgnoreCase(type)) {
            javaGenerator = new EnhanceSimpleJavaClientGenerator();
        } else if ("ANNOTATEDMAPPER".equalsIgnoreCase(type)) {
            javaGenerator = new SimpleAnnotatedClientGenerator();
        } else if ("MAPPER".equalsIgnoreCase(type)) {
            javaGenerator = new SimpleJavaClientGenerator();
        } else {
            javaGenerator = (AbstractJavaClientGenerator)ObjectFactory.createInternalObject(type);
        }

        return javaGenerator;
    }



}
