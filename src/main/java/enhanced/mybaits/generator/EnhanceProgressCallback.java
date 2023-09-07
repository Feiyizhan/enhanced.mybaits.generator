
package enhanced.mybaits.generator;

import org.mybatis.generator.internal.NullProgressCallback;

/**
 * MyBaits Generatior 增强处理进度回调处理
 * @author 徐明龙 XuMingLong 
 */
public class EnhanceProgressCallback extends NullProgressCallback {


    @Override
    public void generationStarted(int totalTasks) {
        System.out.println("生成代码任务开始,待处理的任务数" + totalTasks);
    }


    @Override
    public void introspectionStarted(int totalTasks) {
        System.out.println("解析表数据任务开始,待处理的任务数" + totalTasks);
    }


    @Override
    public void saveStarted(int totalTasks) {
        System.out.println("保存为文件任务开始,待处理的任务数" + totalTasks);
    }


    @Override
    public void startTask(String taskName) {
        System.out.println("开始执行任务：" + taskName);
    }


    @Override
    public void done() {
        System.out.println("所有任务完成");
    }

    
}
