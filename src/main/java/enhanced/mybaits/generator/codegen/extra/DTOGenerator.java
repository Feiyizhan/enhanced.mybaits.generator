
package enhanced.mybaits.generator.codegen.extra;

import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstratEnhanceJavaGenerator;
import enhanced.mybaits.generator.codegen.IEnhanceCommentGenerator;
import enhanced.mybaits.generator.dom.java.DTOClass;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.*;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * DTO 类生成器
 * @author 徐明龙 XuMingLong 
 */
public class DTOGenerator extends AbstratEnhanceJavaGenerator {

    public DTOGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 设置DTO生成位置
     * @author 徐明龙 XuMingLong 
     */
    @Override
    public void setTargetProject() {
        this.targetProject = calculateModelProject();
    }

    /**
     * 生成DTO类
     * @author 徐明龙 XuMingLong 
     * @return DTO类
     */
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        //生成DTO
        DTOClass dTOClass = getDTOClass();
        answer.add(dTOClass);
        return answer;
    }

    /**
     * 生成DTO类
     * @author 徐明龙 XuMingLong 
     * @return DTO类
     */
    protected DTOClass getDTOClass() {
        progressCallback.startTask(String.format("准备生成表%s的DTO类", introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
        }
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(calculateDTOClassName());
        DTOClass dTOClass = new DTOClass(type);
        dTOClass.setVisibility(JavaVisibility.PUBLIC);
        mixedContext.setDTOClass(dTOClass);
        //增加注释
        commentGenerator.addJavaFileComment(dTOClass);
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addDTOClassComment(dTOClass, introspectedTable);
        }
        //增加字段
        TopLevelClass baseRecord = this.mixedContext.getBaseRecord();
        List<Field> fieldList = baseRecord.getFields();
        for(Field r: fieldList) {
            Field newField = new Field(r);
            dTOClass.addField(newField);
            dTOClass.addImportedType(newField.getType());
        }
        //增加Lombok注解
        addLombokAnnotation(dTOClass);
        return dTOClass;
    }
    


    /**
     * 计算DTO类名称
     * @author 徐明龙 XuMingLong 
     * @return DTO类名称
     */
    protected String calculateDTOClassName() {
        StringBuilder sb = new StringBuilder();
        sb.append(calculateDTOClassPackage());
        sb.append('.');
        sb.append(this.mixedContext.getBaseRecord().getType().getShortName());
        sb.append("DTO");
        return sb.toString();
    }
    
    /**
     * 计算DTO类的Package
     * @author 徐明龙 XuMingLong 
     * @return DTO类的Package
     */
    protected String calculateDTOClassPackage() {
        String value = this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.EXTRA_DTO_TARGET_PACKAGE_KEY);
        if(!stringHasValue(value)) {
            return null;
        }else {
            return value;
        }
    }
    
}
