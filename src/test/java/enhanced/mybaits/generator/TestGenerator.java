
package enhanced.mybaits.generator;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

        URL url = TestGenerator.class.getClassLoader().getResource(fileName);
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
