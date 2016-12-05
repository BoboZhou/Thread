import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产消费
 * 
 * @包名
 * @类名 PrivderMaker.java
 * @作者 Bobo
 * @创建日期 2016年12月2日下午8:28:25
 * @描述
 * @版本 V 1.0
 */
class Privder {
	private int count;
	Lock lock = new ReentrantLock();
	Condition condition = lock.newCondition();

	// 生产
	public void privder() {
		lock.lock();

		try {
			while (count >= 20) {
				System.out.println("20了");
				condition.await();
			}
			System.out.println(++count);
			condition.signal();
		} catch (Exception e) {
		} finally {
			lock.unlock();
		}
	}

	// 生产
	public void get() {
		lock.lock();

		try {
			while (count == 0) {
				System.out.println("没有了");
				condition.await();
			}
			System.out.println("还有" + --count);
			condition.signal();
		} catch (Exception e) {
		} finally {
			lock.unlock();
		}
	}
}

public class PrivderMaker {
	public static void main(String[] args) {
		final Privder privder = new Privder();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 30; i++) {
					privder.privder();
				}

			}
		}).start();
		;
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 30; i++) {
					privder.get();
				}

			}
		}).start();
		;
	}

}
