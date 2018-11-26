/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.enums;


/**
 * MyBaits Generatior 增强 sql id枚举类
 * @author 徐明龙 XuMingLong 
 */
public enum EnhanceSqlIdEnum {
    
    /**
     * 全字段列表
     * @author 徐明龙 XuMingLong 
     */
    ALL_COLUMN_LIST("all_column_list","Base_Column_List","全字段列表"),
    
    /**
     * 获取指定主键对应的记录
     * @author 徐明龙 XuMingLong 
     */
    GET_BY_PRIMARY_KEY("getByPrimaryKey","selectByPrimaryKey","获取指定主键对应的记录"),
    
    /**
     * 获取指定主键对应的记录并锁定
     * @author 徐明龙 XuMingLong 
     */
    GET_BY_PRIMARY_KEY_AND_LOCKED("getByPrimaryKeyAndLocked","","获取指定主键对应的记录并锁定"),
    
    /**
     * 新增记录
     * @author 徐明龙 XuMingLong 
     */
    INSERT("insert","insert","新增记录"),
    
    /**
     * 删除指定主键的记录
     * @author 徐明龙 XuMingLong 
     */
    DELETE_BY_PRIMARY_KEY("deleteByPrimaryKey","deleteByPrimaryKey","删除指定主键的记录"),
    
    /**
     * 更新指定主键的记录
     * @author 徐明龙 XuMingLong 
     */
    UPDATE_BY_PRIMARY_KEY("updateByPrimaryKey","updateByPrimaryKey","更新指定主键的记录"),
    
    /**
     * 获取所有记录
     * @author 徐明龙 XuMingLong 
     */
    LIST_ALL("listAll","selectAll","获取所有记录"),
    ;

    private final String value;
    private final String oldValue;
    private final String description;
    


    private EnhanceSqlIdEnum(String value, String oldValue,String description) {
        this.value = value;
        this.oldValue = oldValue;
        this.description = description;
    }

    public String getValue() {
        return value;
    }
    
    public String getOldValue() {
        return oldValue;
    }

    public String getDescription() {
        return description;
    }

    public static EnhanceSqlIdEnum resolve(String typeCode) {
        for (EnhanceSqlIdEnum type : values()) {
            if (type.value.equals(typeCode)) {
                return type;
            }
        }
        return null;
    }
    
    public static EnhanceSqlIdEnum resolveByOldValue(String typeCode) {
        for (EnhanceSqlIdEnum type : values()) {
            if (type.oldValue.equals(typeCode)) {
                return type;
            }
        }
        return null;
    }
    
}
