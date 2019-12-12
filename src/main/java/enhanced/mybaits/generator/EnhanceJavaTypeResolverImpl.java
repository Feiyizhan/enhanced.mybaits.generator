
package enhanced.mybaits.generator;

import java.sql.Types;
import java.time.LocalDateTime;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

/**
 * My baits 生成器 Java类型解析增强处理
 * @author 徐明龙 XuMingLong 
 */
public class EnhanceJavaTypeResolverImpl extends JavaTypeResolverDefaultImpl {

    /**
     * 调用默认的Java类型解析器
     * @author 徐明龙 XuMingLong 
     */
    public EnhanceJavaTypeResolverImpl() {
        super();
        this.typeMap.put(Types.TIMESTAMP, new JdbcTypeInformation("TIMESTAMP", //$NON-NLS-1$
            new FullyQualifiedJavaType(LocalDateTime.class.getName())));
    }

}
