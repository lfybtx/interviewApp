import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


@Slf4j
public class DeadlockTest {
        @Test
        public void test01() {
//                两个锁对象
                Object lock1 = new Object();
                Object lock2 = new Object();
                try {
//                        线程1
                        Thread t1 = new Thread(() -> {
                                synchronized (lock1) {

                                        log.debug("等待lock2");
                                        try {
                                                Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                                e.printStackTrace();
                                        }
                                        synchronized (lock2) {
                                                log.debug("获得了两把锁");
                                        }
                                }
                        });
//                线程2
                        Thread t2 = new Thread(() -> {
                                synchronized (lock2) {

                                        log.debug("等待lock1");
                                        try {
                                                Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                                e.printStackTrace();
                                        }

                                        synchronized (lock1) {
                                                log.debug("获得了两把锁");
                                        }
                                }
                        });


                        t1.start();
                        t2.start();

                        t1.join();
                        t2.join();


                        log.debug("程序结束");
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
//
        }
}
