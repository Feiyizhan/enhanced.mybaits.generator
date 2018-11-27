/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator.codegen;

import static org.mybatis.generator.internal.util.messages.Messages.getString;


import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.SimpleXMLMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import enhanced.mybaits.generator.codegen.xmlmapper.AllColumnListElementGenerator;
import enhanced.mybaits.generator.codegen.xmlmapper.EnhanceDeleteByPrimaryKeyElementGenerator;
import enhanced.mybaits.generator.codegen.xmlmapper.EnhanceInsertElementGenerator;
import enhanced.mybaits.generator.codegen.xmlmapper.EnhanceUpdateByPrimaryKeyElementGenerator;
import enhanced.mybaits.generator.codegen.xmlmapper.GetByPrimaryKeyElementGenerator;
import enhanced.mybaits.generator.codegen.xmlmapper.ListAllElementGenerator;

/**
 * My baits 生成器 - 增强的简单XML Mapper生成器
 * @author 徐明龙 XuMingLong 
 */
public class EnhanceSimpleXMLMapperGenerator extends SimpleXMLMapperGenerator {

    /**
     * 生成SQL Mapper
     * @author 徐明龙 XuMingLong 
     * @return 返回mapper节点
     */
    @Override
    protected XmlElement getSqlMapElement() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString("Progress.12", table.toString())); //$NON-NLS-1$
        XmlElement answer = new XmlElement("mapper"); //$NON-NLS-1$
        String namespace = introspectedTable.getMyBatis3SqlMapNamespace();
        answer.addAttribute(new Attribute("namespace", //$NON-NLS-1$
                namespace));

        context.getCommentGenerator().addRootComment(answer);
        
        addAllColumenListElement(answer);
        addInsertElement(answer);
        addDeleteByPrimaryKeyElement(answer);
        addUpdateByPrimaryKeyElement(answer);
        addGetByPrimaryKeyElement(answer);
        addGetByPrimaryKeyAndLockedElement(answer);
        addListAllElement(answer);

        return answer;
    }
    /**
     * 增加全字段列表节点
     * @author 徐明龙 XuMingLong 
     * @param parentElement 待处理的节点
     */
    protected void addAllColumenListElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new AllColumnListElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
    
    /**
     * 增加删除指定主键的记录节点
     * @author 徐明龙 XuMingLong 
     * @param parentElement 待处理的节点
     */
    protected void addDeleteByPrimaryKeyElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateDeleteByPrimaryKey()) {
            AbstractXmlElementGenerator elementGenerator = new EnhanceDeleteByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    /**
     * 增加更新指定主键的记录节点
     * @author 徐明龙 XuMingLong 
     * @param parentElement 待处理的节点
     */
    protected void addUpdateByPrimaryKeyElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateUpdateByPrimaryKeyWithoutBLOBs()) {
            AbstractXmlElementGenerator elementGenerator = new EnhanceUpdateByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    /**
     * 增加新增记录节点
     * @author 徐明龙 XuMingLong 
     * @param parentElement 待处理的节点
     */
    protected void addInsertElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateInsert()) {
            AbstractXmlElementGenerator elementGenerator = new EnhanceInsertElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    
    /**
     * 增加获取指定主键对应的记录节点
     * @author 徐明龙 XuMingLong 
     * @param parentElement 待处理的节点
     */
    protected void addGetByPrimaryKeyElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractXmlElementGenerator elementGenerator = new GetByPrimaryKeyElementGenerator(false);
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    /**
     * 增加获取指定主键对应的记录并锁定节点
     * @author 徐明龙 XuMingLong 
     * @param parentElement 待处理的节点
     */
    protected void addGetByPrimaryKeyAndLockedElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractXmlElementGenerator elementGenerator = new GetByPrimaryKeyElementGenerator(true);
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    /**
     * 增加获取所有记录节点
     * @author 徐明龙 XuMingLong 
     * @param parentElement 待处理的节点
     */
    protected void addListAllElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new ListAllElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
    
    
    
    
}
