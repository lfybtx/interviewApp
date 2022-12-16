import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

@Slf4j
public class SemaphoreTest {

        @Test
        public void test01() {
//                初始化信号量
                Semaphore mutex = new Semaphore(1);
                Semaphore emptyBuffers = new Semaphore(10);
                Semaphore fullBuffers = new Semaphore(0);
//                生产者线程
                new Thread(() ->
                {
                        while (true) {
                                try {
                                        emptyBuffers.acquire();
                                        mutex.acquire();
                                        log.debug("生成数据放入到缓冲区");
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                } finally {
                                        fullBuffers.release();
                                        mutex.release();
                                }
                        }
                }).start();
//                消费者线程
                new Thread(() ->
                {
                        while (true) {
                                try {
                                        fullBuffers.acquire();
                                        mutex.acquire();
                                        log.debug("从缓冲区中读取数据");
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                } finally {
                                        emptyBuffers.release();
                                        mutex.release();
                                }
                        }
                }).start();
        }
}
