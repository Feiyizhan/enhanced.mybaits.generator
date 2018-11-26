/**
 * 版权归上海亚商投资顾问有限公司所有，未经本公司协议授权，禁止任何使用、篡改等行为。
 * Copyright©2018【ABC Financial Service】All Rights Reserved. No part may be used or tampered in any form or means without the prior written consent.
 */
package enhanced.mybaits.generator;

import org.mybatis.generator.internal.NullProgressCallback;

import lombok.extern.log4j.Log4j2;

/**
 * MyBaits Generatior 增强处理进度回调处理
 * @author 徐明龙 XuMingLong 
 */
@Log4j2
public class EnhanceProgressCallback extends NullProgressCallback {


    @Override
    public void generationStarted(int totalTasks) {
        log.info("生成代码任务开始");
    }


    @Override
    public void introspectionStarted(int totalTasks) {
        log.info("解析表数据任务开始，任务数量:{}",totalTasks);
    }


    @Override
    public void saveStarted(int totalTasks) {
        log.info("保存为文件任务开始，任务数量:{}",totalTasks);
    }


    @Override
    public void startTask(String taskName) {
        log.info("开始执行任务:{}",taskName);
    }


    @Override
    public void done() {
        log.info("所有任务完成");
    }

    
}
