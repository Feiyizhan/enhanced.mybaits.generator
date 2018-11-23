/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.enums;

/**
 * Service 接口实现类扩展方法名称枚举类
 * @author 徐明龙 XuMingLong 
 * @createDate 2018-11-21 
 */
public enum ServiceImplExtraMethodEnum {

    /**
     * 新增表单校验
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     */
    VERIFY_FORM_FOR_INSERT("verifyFormForInsert","新增表单校验"),
    
    /**
     * 修改表单校验
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     */
    VERIFY_FORM_FOR_UPDATE("verifyFormForUpdate","修改表单校验"),
    
    /**
     * 删除表单校验
     * @author 徐明龙 XuMingLong 
     * @createDate 2018-11-21 
     */
    VERIFY_FORM_FOR_DELETE("verifyFormForDelete","删除表单校验"),
    
    
    ;
    
    private final String value;
    private final String description;
    


    private ServiceImplExtraMethodEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }
    
   

    public String getDescription() {
        return description;
    }

    public static ServiceImplExtraMethodEnum resolve(String typeCode) {
        for (ServiceImplExtraMethodEnum type : values()) {
            if (type.value.equals(typeCode)) {
                return type;
            }
        }
        return null;
    }
    
}
