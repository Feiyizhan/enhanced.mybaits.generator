/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.core.util.FileUtils;
import org.junit.jupiter.api.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;

/**
 * 生成器测试类
 * @author 徐明龙 XuMingLong 2018-11-29
 */
public class TestGenerator {

    /**
     * 获取指定文件
     * @author 徐明龙 XuMingLong 2018-11-29
     * @param fileName
     * @return
     */
    public static File getFile(String fileName){

        File file= new File(fileName);
        if(file.exists()) {
            return file;
        }

        URL url = FileUtils.class.getClassLoader().getResource(fileName);
        if(url!=null){
            try {
                fileName = URLDecoder.decode(url.getFile(),"UTF-8");
            } catch (UnsupportedEncodingException e) {}
        }

        file= new File(fileName);
        return file;
    }

    /**
     * 测试生成器
     * @author 徐明龙 XuMingLong 2018-11-29
     * @throws IOException
     * @throws XMLParserException
     * @throws InvalidConfigurationException
     * @throws SQLException
     * @throws InterruptedException
     */
    @Test
    public void testGenerator() throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
        List<String> warnings = new ArrayList<String>();
        File configFile = getFile("generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, null, warnings);
        EnhanceProgressCallback callback = new EnhanceProgressCallback();
        myBatisGenerator.generate(callback);
    }
}
