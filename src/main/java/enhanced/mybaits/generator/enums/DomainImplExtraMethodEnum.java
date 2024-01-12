
package enhanced.mybaits.generator.enums;

/**
 * Domain 接口实现类扩展方法名称枚举类
 * @author 徐明龙 XuMingLong 
 */
public enum DomainImplExtraMethodEnum {

    /**
     * 新增校验
     * @author 徐明龙 XuMingLong
     */
    VERIFY_FOR_INSERT("verifyForAddOnce","新增校验"),

    /**
     * 修改校验
     * @author 徐明龙 XuMingLong
     */
    VERIFY_FOR_UPDATE("verifyForUpdate","修改校验"),

    /**
     * 删除校验
     * @author 徐明龙 XuMingLong
     */
    VERIFY_FOR_DELETE("verifyForDelete","删除校验"),


    ;

    private final String value;
    private final String description;



    private DomainImplExtraMethodEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }
    
   

    public String getDescription() {
        return description;
    }

    public static DomainImplExtraMethodEnum resolve(String typeCode) {
        for (DomainImplExtraMethodEnum type : values()) {
            if (type.value.equals(typeCode)) {
                return type;
            }
        }
        return null;
    }
    
}
