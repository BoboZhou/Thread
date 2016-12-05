import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程的id abc 要求将每个线程的id abc 依次打印十遍
 * 
 * @包名
 * @类名 Test1.java
 * @作者 Bobo
 * @创建日期 2016年12月2日下午12:23:31
 * @描述 线程 操作 资源
 * @版本 V 1.0
 */
public class Test1 {
	ReentrantLock lock = new ReentrantLock();
	Condition a = lock.newCondition();
	Condition b = lock.newCondition();
	Condition c = lock.newCondition();
	String flag = "A";

	public void print(String str) {
		lock.lock();

		try {
			while (!str.equals(flag)) {
				if (str.equals("B")) {
					b.await();
				} else if (str.equals("A")) {
					a.await();
				} else {
					c.await();
				}

			}
			System.out.println(str);
			if (flag.equals("A")) {
				flag = "B";
				b.signal();
			} else if (flag.equals("B")) {
				flag = "C";
				c.signal();
			} else {
				flag = "A";
				a.signal();
			}

		} catch (Exception e) {

		} finally {

			lock.unlock();
		}

	}

	public static void main(String[] args) {
		final Test1 test1 = new Test1();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					test1.print("B");
				}

			}
		}).start();
		;
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					test1.print("A");
				}

			}
		}).start();
		;
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					test1.print("C");
				}

			}
		}).start();
		;

	}
}
