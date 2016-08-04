package com.hql.lightning;

import com.hql.lightning.buffer.GameUpBuffer;
import com.hql.lightning.core.GameBoss;
import com.hql.lightning.core.GameUpProcessor;
import com.hql.lightning.core.GameWorkerManager;
import com.hql.lightning.core.ServerInit;

/**
 * server启动
 */
public class Startup {

    public void run() throws Exception {
        ServerInit.getInstance().initConfPath("confFiles");
        ServerInit.getInstance().initLog4j();
        ServerInit.getInstance().initGameWorkers();
        ServerInit.getInstance().initModules();

        //TimerTaskUtil.getInstance().run(new AutoUpdateTest(), 10, TimeUnit.SECONDS);

        GameBoss.getInstance().boot(new GameUpProcessor() {
            @Override
            public void process(GameUpBuffer buffer) {
                GameWorkerManager.getInstance().pushDataToWorker(buffer);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        new Startup().run();
    }
}