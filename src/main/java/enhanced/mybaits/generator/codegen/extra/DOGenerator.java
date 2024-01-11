
package enhanced.mybaits.generator.codegen.extra;

import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstratEnhanceJavaGenerator;
import enhanced.mybaits.generator.codegen.IEnhanceCommentGenerator;
import enhanced.mybaits.generator.dom.java.DOClass;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.*;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * DO 类生成器
 * @author 徐明龙 XuMingLong 
 */
public class DOGenerator extends AbstratEnhanceJavaGenerator {

    public DOGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 设置DO生成位置
     * @author 徐明龙 XuMingLong 
     */
    @Override
    public void setTargetProject() {
        this.targetProject = calculateModelProject();
    }

    /**
     * 生成DO类
     * @author 徐明龙 XuMingLong 
     * @return DO类
     */
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        //生成DO
        DOClass dOClass = getDOClass();
        answer.add(dOClass);
        return answer;
    }

    /**
     * 生成DO类
     * @author 徐明龙 XuMingLong 
     * @return DO类
     */
    protected DOClass getDOClass() {
        progressCallback.startTask(String.format("准备生成表%s的DO类", introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
        }
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(calculateDOClassName());
        DOClass dOClass = new DOClass(type);
        dOClass.setVisibility(JavaVisibility.PUBLIC);
        mixedContext.setDOClass(dOClass);
        //增加注释
        commentGenerator.addJavaFileComment(dOClass);
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addDOClassComment(dOClass, introspectedTable);
        }
        //增加字段
        TopLevelClass baseRecord = this.mixedContext.getBaseRecord();
        List<Field> fieldList = baseRecord.getFields();
        for(Field r: fieldList) {
            Field newField = new Field(r);
            dOClass.addField(newField);
            dOClass.addImportedType(newField.getType());
        }
        //增加Lombok注解
        addLombokAnnotation(dOClass);
        return dOClass;
    }
    


    /**
     * 计算DO类名称
     * @author 徐明龙 XuMingLong 
     * @return DO类名称
     */
    protected String calculateDOClassName() {
        StringBuilder sb = new StringBuilder();
        sb.append(calculateDOClassPackage());
        sb.append('.');
        sb.append(this.mixedContext.getBaseRecord().getType().getShortName());
        sb.append("DO");
        return sb.toString();
    }
    
    /**
     * 计算DO类的Package
     * @author 徐明龙 XuMingLong 
     * @return DO类的Package
     */
    protected String calculateDOClassPackage() {
        String value = this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.EXTRA_DO_TARGET_PACKAGE_KEY);
        if(!stringHasValue(value)) {
            return null;
        }else {
            return value;
        }
    }
    
}
