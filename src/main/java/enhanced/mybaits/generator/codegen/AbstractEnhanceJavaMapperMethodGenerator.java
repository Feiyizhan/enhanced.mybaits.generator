
package enhanced.mybaits.generator.codegen;

import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

/**
 * 增强的Java Mapper方法生成器基础类
 * 
 * @author 徐明龙 XuMingLong
 */
public abstract class AbstractEnhanceJavaMapperMethodGenerator extends AbstractJavaMapperMethodGenerator {

    public AbstractEnhanceJavaMapperMethodGenerator() {
        super();
    }

    public void addMapperAnnotations(Interface interfaze, Method method) {}

    public void addExtraImports(Interface interfaze) {}

}
