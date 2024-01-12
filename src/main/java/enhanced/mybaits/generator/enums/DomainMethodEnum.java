
package enhanced.mybaits.generator.enums;

/**
 * Domain 方法名称枚举类
 * @author 徐明龙 XuMingLong 
 */
public enum DomainMethodEnum {

    /**
     * 新增记录
     * @author 徐明龙 XuMingLong
     */
    INSERT("addOnce","新增记录"),

    /**
     * 更新指定记录
     * @author 徐明龙 XuMingLong
     */
    UPDATE_BY_PRIMARY_KEY("updateBy${PrimaryKey}","更新指定记录"),

    /**
     * 删除指定记录
     * @author 徐明龙 XuMingLong
     */
    DELETE_BY_PRIMARY_KEY("deleteBy${PrimaryKey}","删除指定记录"),

    /**
     * 获取主键对应的记录
     * @author 徐明龙 XuMingLong
     */
    GET_BY_PRIMARY_KEY("getBy${PrimaryKey}","获取主键对应的记录"),

    /**
     * 获取主键对应的记录的返回结果对象
     * @author 徐明龙 XuMingLong
     */
    GET_AND_LOCK_BY_PRIMARY_KEY("getAndLockBy${PrimaryKey}","获取并锁定主键对应的记录"),

    /**
     * 获取满足搜索请求的记录数
     * @author 徐明龙 XuMingLong
     */
    COUNT_FOR_PAGE("countForPage"," 获取满足搜索请求的记录数"),

    /**
     * 获取满足搜索请求的分页记录
     * @author 徐明龙 XuMingLong
     */
    LIST_FOR_PAGE("listForPage","获取满足搜索请求的分页记录"),
    ;

    private final String value;
    private final String description;



    private DomainMethodEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }
    
   

    public String getDescription() {
        return description;
    }

    public static DomainMethodEnum resolve(String typeCode) {
        for (DomainMethodEnum type : values()) {
            if (type.value.equals(typeCode)) {
                return type;
            }
        }
        return null;
    }
    
    /**
     * 获取替换了主键值的方法名
     * @author 徐明龙 XuMingLong 
     * @param primaryKeyValue 主键值
     * @return 替换了主键值的方法名
     */
    public String getReplacePrimaryKeyValue(String primaryKeyValue) {
        return this.value.replace("${PrimaryKey}", primaryKeyValue);
    }
    
}
