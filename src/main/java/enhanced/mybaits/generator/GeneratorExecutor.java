/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PropertyRegistry;

import enhanced.mybaits.generator.codegen.AbstratEnhanceJavaGenerator;

/**
 * 生成器执行者
 * @author 徐明龙 XuMingLong 
 */
public class GeneratorExecutor {
    private List<AbstratEnhanceJavaGenerator> javaGeneratorList;
    
    protected Context context;
    
    protected IntrospectedTable table;
    
    private List<String> warnings;
    
    private EnhanceProgressCallback callback = new EnhanceProgressCallback(); 
    
    public GeneratorExecutor() {
        javaGeneratorList = new ArrayList<>();
    }
    
    public void setContext(Context context) {
        this.context = context;
    }

    public void setTable(IntrospectedTable table) {
        this.table = table;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    /**
     * 增加生成器
     * @author 徐明龙 XuMingLong 2018-11-26 
     * @param javaGenerator Java代码生成器
     */
    public void addJavaGenerator(AbstratEnhanceJavaGenerator javaGenerator ) {
        javaGeneratorList.add(javaGenerator);
    }
    
    /**
     * 生成所有文件
     * @author 徐明龙 XuMingLong 2018-11-26 
     * @return  返回生成的Java代码文件列表
     */
    public List<GeneratedJavaFile> generateAllFiles(){
        List<GeneratedJavaFile> answer = new ArrayList<GeneratedJavaFile>();
        for(AbstratEnhanceJavaGenerator generator:javaGeneratorList) {
            initializeAbstractGenerator(generator);
            List<CompilationUnit> compilationUnits = generator.getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                    generator.getTargetProject(),
                    context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                    context.getJavaFormatter());
                answer.add(gjf);
            }
        }
        return answer;
    }
    
    /**
     * 初始化构建器
     * @author 徐明龙 XuMingLong 
     * @param abstractGenerator 待初始的构建器
     */
    protected void initializeAbstractGenerator(AbstratEnhanceJavaGenerator abstractGenerator) {
        abstractGenerator.setContext(context);
        abstractGenerator.setIntrospectedTable(table);
        abstractGenerator.setProgressCallback(callback);
        abstractGenerator.setWarnings(warnings);
        abstractGenerator.setTargetProject();
    }
    
    
}
