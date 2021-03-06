
package enhanced.mybaits.generator.codegen.extra;

import enhanced.mybaits.generator.EnhanceConstant;
import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstratEnhanceJavaGenerator;
import enhanced.mybaits.generator.codegen.IEnhanceCommentGenerator;
import enhanced.mybaits.generator.dom.java.ResultClass;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * Result类生成器
 * @author 徐明龙 XuMingLong 
 */
public class ResultGenerator extends AbstratEnhanceJavaGenerator {

    public ResultGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }

    /**
     * 设置生成的目标项目目录
     * @author 徐明龙 XuMingLong 
     */
    @Override
    public void setTargetProject() {
        this.targetProject = calculateModelProject();
    }

    /**
     * 获取Result类
     * @author 徐明龙 XuMingLong 
     * @return Result类
     */
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        //生成Service 接口类
        ResultClass resultClass = getResultClass();
        answer.add(resultClass);
        return answer;
    }
    
    /**
     * 生成Result类
     * @author 徐明龙 XuMingLong 
     * @return Result类
     */
    protected ResultClass getResultClass() {
        progressCallback.startTask(String.format("准备生成表%s的Result类", introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();
        IEnhanceCommentGenerator enhanceCommentGenerator = null ;
        if(commentGenerator instanceof IEnhanceCommentGenerator) {
            enhanceCommentGenerator = (IEnhanceCommentGenerator) commentGenerator;
        }
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(calculateResutlClassName());
        ResultClass resultClass = new ResultClass(type);
        resultClass.setVisibility(JavaVisibility.PUBLIC);
        mixedContext.setResultClass(resultClass);
        //增加注释
        commentGenerator.addJavaFileComment(resultClass);
        if(enhanceCommentGenerator!=null) {
            enhanceCommentGenerator.addResultClassComment(resultClass, introspectedTable);
        }
        //增加字段
        TopLevelClass baseRecord = this.mixedContext.getBaseRecord();
        List<Field> fieldList = baseRecord.getFields();
        List<IntrospectedColumn> allColumnList = this.introspectedTable.getAllColumns();
        boolean hasField = false;
        for(Field r: fieldList) {
            String remark = "";
            for(IntrospectedColumn column:allColumnList) {
                if(r.getName().equals(column.getJavaProperty())) {
                    remark = column.getRemarks();
                    break;
                }
            }
            Field newField = new Field(r);
            //增加Swagger注解
            newField.addAnnotation(String.join("", "@ApiModelProperty(value=\"",remark,"\")"));
            resultClass.addField(newField);
            resultClass.addImportedType(newField.getType());
            hasField = true;
        }
        //增加引入
        if(hasField) {
            resultClass.addImportedType("io.swagger.annotations.ApiModelProperty");
        }
        //增加Lombok注解
        addLombokAnnotation(resultClass);
        //增加Swagger2注解
        addSwagger2Annotation(resultClass,"返回结果");
        return resultClass;
    }

    /**
     * 计算Result类名称
     * @author 徐明龙 XuMingLong 
     * @return Result类名称
     */
    protected String calculateResutlClassName() {
        StringBuilder sb = new StringBuilder();
        sb.append(calculateResultClassPackage());
        sb.append('.');
        sb.append(this.mixedContext.getBaseRecord().getType().getShortName());
        sb.append("Result"); 
        return sb.toString();
    }
    
    /**
     * 计算Result类的Package
     * @author 徐明龙 XuMingLong 
     * @return Result类的Package
     */
    protected String calculateResultClassPackage() {
        String value = this.context
            .getJavaClientGeneratorConfiguration()
            .getProperty(EnhanceConstant.EXTRA_RESULT_TARGET_PACKAGE_KEY);
        if(!stringHasValue(value)) {
            return null;
        }else {
            return value;
        }
    }

}
