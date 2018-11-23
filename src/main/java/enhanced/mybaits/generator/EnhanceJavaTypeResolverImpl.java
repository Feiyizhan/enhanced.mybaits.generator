/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator;

import java.sql.Types;
import java.time.LocalDateTime;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

/**
 * My baits 生成器 Java类型解析增强处理
 * @author 徐明龙 XuMingLong 
 * @createDate 2018-11-14 
 */
public class EnhanceJavaTypeResolverImpl extends JavaTypeResolverDefaultImpl {

    /**
     * 调用默认的Java类型解析器
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-14 
     */
    public EnhanceJavaTypeResolverImpl() {
        super();
        this.typeMap.put(Types.TIMESTAMP, new JdbcTypeInformation("TIMESTAMP", //$NON-NLS-1$
            new FullyQualifiedJavaType(LocalDateTime.class.getName())));
    }

}
