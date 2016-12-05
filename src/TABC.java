import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程的id abc 要求将每个线程的id abc 依次打印十遍
 * 
 * @类名 TABC.java
 * @作者 Bobo
 * @创建日期 2016年12月2日下午6:51:46
 * @描述
 * @版本 V 1.0
 */
class ABC {

}

public class TABC {
	Lock lock = new ReentrantLock();
	Condition a = lock.newCondition();
	Condition b = lock.newCondition();
	Condition c = lock.newCondition();
	boolean flagA = true;
	boolean flagB = false;
	boolean flagC = false;

	// 线程操纵资源
	public void printA() {
		String name = Thread.currentThread().getName();

		try {
			lock.lock();
			// while (name.equals("A") && flagA == false) {
			// flagA = true;
			// }
			while (!flagA) {
				a.await();
			}

			System.out.println(Thread.currentThread().getName());
			flagA = false;
			flagC = false;
			flagB = true;
			b.signal();

		} catch (Exception e) {
		} finally {
			lock.unlock();
		}

	}

	// 线程操纵资源
	public void printB() {
		String name = Thread.currentThread().getName();

		try {
			lock.lock();

			while (!flagB) {
				b.await();
				Thread.currentThread().getName();
			}

			System.out.println(Thread.currentThread().getName());
			flagB = false;
			flagC = true;
			flagA = false;
			c.signal();

		} catch (Exception e) {
		} finally {
			lock.unlock();
		}

	}

	// 线程操纵资源
	public void printC() {
		String name = Thread.currentThread().getName();

		try {
			lock.lock();

			while (!flagC) {
				c.await();
			}

			System.out.println(Thread.currentThread().getName());
			flagC = false;
			flagA = true;
			flagB = false;
			a.signal();

		} catch (Exception e) {
		} finally {
			lock.unlock();
		}

	}

	public static void main(String[] args) {
		final TABC tabc = new TABC();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					tabc.printA();
				}

			}
		}, "A").start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					tabc.printB();
				}

			}
		}, "B").start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					tabc.printC();
				}

			}
		}, "C").start();
	}

}
