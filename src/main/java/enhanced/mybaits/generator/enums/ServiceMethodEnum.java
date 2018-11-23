/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.enums;

/**
 * Service 方法名称枚举类
 * @author 徐明龙 XuMingLong 
 * @createDate 2018-11-21 
 */
public enum ServiceMethodEnum {

    /**
     * 新增记录
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     */
    INSERT("insert","新增记录"),
    
    /**
     * 更新指定记录
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     */
    UPDATE_BY_PRIMARY_KEY("updateBy${PrimaryKey}","更新指定记录"),
    
    /**
     * 删除指定记录
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     */
    DELETE_BY_PRIMARY_KEY("deleteBy${PrimaryKey}","删除指定记录"),
    
    /**
     * 获取主键对应的记录
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     */
    GET_BY_PRIMARY_KEY("getBy${PrimaryKey}","获取主键对应的记录"),
    
    /**
     * 获取主键对应的记录的返回结果对象
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     */
    GET_RESULT_BY_PRIMARY_KEY("getResultBy${PrimaryKey}","获取主键对应的记录的返回结果对象"),
    
    
    /**
     * 分页搜索并获取返回结果对象列表
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     */
    LIST_RESULT_FOR_PAGE("listResultForPage","分页搜索并获取返回结果对象列表"),
    ;
    
    private final String value;
    private final String description;
    


    private ServiceMethodEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }
    
   

    public String getDescription() {
        return description;
    }

    public static ServiceMethodEnum resolve(String typeCode) {
        for (ServiceMethodEnum type : values()) {
            if (type.value.equals(typeCode)) {
                return type;
            }
        }
        return null;
    }
    
    /**
     * 获取替换了主键值的方法名
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-23 
     * @param primaryKeyValue
     * @return
     */
    public String getReplacePrimaryKeyValue(String primaryKeyValue) {
        return this.value.replace("${PrimaryKey}", primaryKeyValue);
    }
    
}
