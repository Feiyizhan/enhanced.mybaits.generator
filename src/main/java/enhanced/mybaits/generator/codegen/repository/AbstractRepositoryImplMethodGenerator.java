
package enhanced.mybaits.generator.codegen.repository;


import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.enums.AudiFieldEnum;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;

import java.util.List;

/**
 * Repository 接口实现类方法生成器基类
 * @author 徐明龙 XuMingLong
 */
public abstract class AbstractRepositoryImplMethodGenerator extends AbstractRepositoryInterfaceMethodGenerator {


    /**
     * 获取表对应的实体类变量名称
     * @author 徐明龙 XuMingLong
     */
    protected String baseRecordVarName ;
    /**
     * Mapper字段名称
     * @author 徐明龙 XuMingLong
     */
    protected String mapperFieldName;
    /**
     * 对应的接口方法
     * @author 徐明龙 XuMingLong
     */
    protected Method interfaceMethod;

    public AbstractRepositoryImplMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
        this.baseRecordVarName = getBaseRecordVarName();
        this.mapperFieldName = getMapperFieldName();
    }



    /**
     * 获取表对应的实体类变量名称
     * @author 徐明龙 XuMingLong
     * @return 返回基础实体变量名
     */
    protected String getBaseRecordVarName() {
        return StringUtils.uncapitalize(this.mixedContext.getBaseRecord().getType().getShortNameWithoutTypeArguments());
    }

    /**
     * 增加方法
     * @author 徐明龙 XuMingLong
     */
    @Override
    public void addMethod() {
        //设置对应的接口方法
        setInterfaceMethod();
        Method method =null ;
        if(interfaceMethod!=null) {
            method = new Method(interfaceMethod);
            method.setVisibility(JavaVisibility.PUBLIC);
            method.addAnnotation("@Override");
            //增加方法内容
            addMethodBody(method);
            this.mixedContext.getRepositoryImplClass().addMethod(method);
        }

        List<Method> extraMethodList = addExtraMethod(method);
        if(extraMethodList!=null && extraMethodList.size() >0) {
            extraMethodList.forEach((r)->{
                this.mixedContext.getRepositoryImplClass().addMethod(r);
            });
        }
    }




    /**
     * 增加额外的方法
     * @author 徐明龙 XuMingLong
     * @param method 待处理的方法
     * @return 额外的方法
     */
    protected abstract List<Method> addExtraMethod(Method method);

    /**
     * 增加方法内容
     * @author 徐明龙 XuMingLong
     * @param method 待处理的方法
     */
    protected abstract void addMethodBody(Method method) ;

    /**
     * 设置对应的接口方法
     * @author 徐明龙 XuMingLong
     */
    protected void setInterfaceMethod() {
        String methodName = calculateMethodName();
        List<Method> methodList = this.mixedContext.getRepositoryInterface().getMethods();
        for(Method r:methodList) {
            if(r.getName().equals(methodName)) {
                this.interfaceMethod = r;
                break;
            }
        }
    }


    /**
     * 设置方法返回类型
     * @author 徐明龙 XuMingLong
     * @param method 待处理的方法
     */
    @Override
    protected void setMethodReturnType(Method method) {

    }

    /**
     * 增加方法参数
     * @author 徐明龙 XuMingLong
     * @param method 待处理的方法
     */
    @Override
    protected void addMethodParameter(Method method) {

    }


    /**
     * 增加表对应的实体的所有审计信息代码
     * @author 徐明龙 XuMingLong 
     * @param method 待处理的方法
     */
    protected void addBaseRecordAllAudiInfoCode(Method method) {
        List<Field> fieldList = this.mixedContext.getBaseRecord().getFields();
        for(Field r:fieldList) {
            AudiFieldEnum audiField = AudiFieldEnum.resolve(r.getName());
            if(audiField==null) {
                continue;
            }
            switch (audiField) {
                case CREATE_DATE:
                case UPDATE_DATE:    
                    addBaseRecordAudiFieldCode(method,r,nowVarName);
                    break;
                case CREATOR:
                case MODIFIER:    
                    addBaseRecordAudiFieldCode(method,r,
                        StringUtils.join(userVarName,".getName()"));
                    break;
                case CREATOR_ID:
                case MODIFIER_ID:    
                    addBaseRecordAudiFieldCode(method,r,
                        StringUtils.join(userVarName,".getId()"));
                    break;
                    
                default:
                    break;
            }
       
        }
    }
    /**
     * 增加表对应的实体的审计字段信息代码
     * @author 徐明龙 XuMingLong
     * @param mothod 待处理的方法
     * @param field  审计字段名称
     * @param varName 审计字段值
     */
    protected void addBaseRecordAudiFieldCode(Method mothod,Field field,String varName) {
        mothod.addBodyLine(
            StringUtils.join(
                baseRecordVarName,
                ".set",
                StringUtils.capitalize(field.getName()),
                "(",
                varName,
                ");"
                )
            );
    }



    /**
     * 增加表对应的实体的更新审计信息代码
     * @author 徐明龙 XuMingLong 
     * @param method 待处理的方法
     */
    protected void addBaseRecordUpdateAudiInfoCode(Method method) {
        List<Field> fieldList = this.mixedContext.getBaseRecord().getFields();
        for(Field r:fieldList) {
            AudiFieldEnum audiField = AudiFieldEnum.resolve(r.getName());
            if(audiField==null) {
                continue;
            }
            switch (audiField) {
                case UPDATE_DATE:    
                    addBaseRecordAudiFieldCode(method,r,nowVarName);
                    break;
                case MODIFIER:    
                    addBaseRecordAudiFieldCode(method,r,
                        StringUtils.join(userVarName,".getName()"));
                    break;
                case MODIFIER_ID:    
                    addBaseRecordAudiFieldCode(method,r,
                        StringUtils.join(userVarName,".getId()"));
                    break;
                    
                default:
                    break;
            }
       
        }
    }
    
    /**
     * 获取Mapper字段名称
     * @author 徐明龙 XuMingLong 
     * @return 待注入的Mapper字段名称
     */
    protected String getMapperFieldName() {
        return StringUtils.uncapitalize(this.mixedContext.getMapper().getType().getShortName());
    }

}
