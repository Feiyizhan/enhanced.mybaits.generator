/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.enums;

/**
 *
 * @author 徐明龙 XuMingLong 
 */
public enum AudiFieldEnum {
    /**
     * 创建日期
     * @author 徐明龙 XuMingLong 
     */
    CREATE_DATE("createDate","创建日期"),
    
    /**
     * 创建人
     * @author 徐明龙 XuMingLong 
     */
    CREATOR("creator","创建人"),
    
    /**
     * 创建人id
     * @author 徐明龙 XuMingLong 
     */
    CREATOR_ID("creatorId","创建人id"),
    
    /**
     * 修改日期
     * @author 徐明龙 XuMingLong 
     */
    UPDATE_DATE("updateDate","修改日期"),
    
    /**
     * 修改人
     * @author 徐明龙 XuMingLong 
     */
    MODIFIER("modifier","修改人"),
    
    
    /**
     * 修改人id
     * @author 徐明龙 XuMingLong 
     */
    MODIFIER_ID("modifierId","修改人id"), 
    ;
    
    private final String value;
    private final String description;
    


    private AudiFieldEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }
    
   

    public String getDescription() {
        return description;
    }

    public static AudiFieldEnum resolve(String typeCode) {
        for (AudiFieldEnum type : values()) {
            if (type.value.equals(typeCode)) {
                return type;
            }
        }
        return null;
    }
}
