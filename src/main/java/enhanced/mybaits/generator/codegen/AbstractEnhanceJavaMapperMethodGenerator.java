/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。 Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be
 * used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.codegen;

import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

/**
 * 增强的Java Mapper方法生成器基础类
 * 
 * @author 徐明龙 XuMingLong
 * @createDate 2018-11-16
 */
public abstract class AbstractEnhanceJavaMapperMethodGenerator extends AbstractJavaMapperMethodGenerator {

    public AbstractEnhanceJavaMapperMethodGenerator() {
        super();
    }

    public void addMapperAnnotations(Interface interfaze, Method method) {}

    public void addExtraImports(Interface interfaze) {}

}
