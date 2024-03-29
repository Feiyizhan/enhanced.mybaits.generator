
package enhanced.mybaits.generator.codegen.test;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import enhanced.mybaits.generator.MixedContext;
import enhanced.mybaits.generator.codegen.AbstractMapperTestsMethodGenerator;

/**
 * 测试类的新增方法测试生成器
 * @author 徐明龙 XuMingLong 
 */
public class TestsInsertMethodGenerator extends AbstractMapperTestsMethodGenerator{

    public TestsInsertMethodGenerator(MixedContext mixedContext) {
        super(mixedContext);
    }


    /**
     * 增加测试方法的内容
     * @author 徐明龙 XuMingLong 
     * @param method 方法名称
     */
    @Override
    protected void addMethodBody(Method method) {
        //增加方法的代码
        StringBuilder sb = new StringBuilder();
        //构建参数对象
        List<Parameter> parameterList = testedMethod.getParameters();
        List<String> parameterNameList = new ArrayList<>();
        for(Parameter r:parameterList) {
            FullyQualifiedJavaType type = r.getType();
            String name = type.getShortName();
            String varName = StringUtils.uncapitalize(name);
            parameterNameList.add(varName);
            sb.append(name);
            sb.append(" ");
            sb.append(varName);
            sb.append(" = new ");
            sb.append(name);
            sb.append("(");
            sb.append(");");
            method.addBodyLine(sb.toString());
            sb.setLength(0);
        }
        
        
        sb.append("// TODO 请补充完整测试代码");
        method.addBodyLine(sb.toString());
        sb.setLength(0);
        
        //调用Mapper类的测试方法
        sb.append("int count = ");
        sb.append(testedMapperField.getName());
        sb.append(".");
        sb.append(testedMethod.getName());
        sb.append("(");
        sb.append(StringUtils.join(parameterNameList, ","));
        sb.append(");");
        method.addBodyLine(sb.toString());
        sb.setLength(0);
        //调用assertThat方法
        sb.append("assertThat(count).isNotZero();");
        method.addBodyLine(sb.toString());
        sb.setLength(0);
    }

    
    /**
     * 设置被测试方法
     * @author 徐明龙 XuMingLong 
     */
    @Override
    protected void setTestedMethod() {
        List<Method> methodList = mixedContext.getMapper().getMethods();
        for(Method r:methodList) {
            if(r.getName().equals(introspectedTable.getInsertStatementId())) {
                testedMethod = r;
                break;
            }
        }
    }
    
    
    
    



}
