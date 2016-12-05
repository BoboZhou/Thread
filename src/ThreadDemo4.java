import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareResource {
	private int number = 1;
	private Lock lock = new ReentrantLock();
	private Condition condition1 = lock.newCondition();
	private Condition condition2 = lock.newCondition();
	private Condition condition3 = lock.newCondition();

	public void LoopA(int totalLoop) {
		lock.lock();
		try {
			// 1 判断
			while (number != 1) {
				condition1.await();
			}
			// 2干活
			for (int i = 1; i <= 5; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i
						+ "\t总轮询次数：" + totalLoop);
			}
			// 3唤醒下一个线程
			number = 2;
			condition2.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void LoopB(int totalLoop) {
		lock.lock();
		try {
			// 1 判断
			while (number != 2) {
				condition2.await();
			}
			// 2干活
			for (int i = 1; i <= 10; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i
						+ "\t总轮询次数：" + totalLoop);
			}
			// 3唤醒下一个线程
			number = 3;
			condition3.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void LoopC(int totalLoop) {
		lock.lock();
		try {
			// 1 判断
			while (number != 3) {
				condition3.await();
			}
			// 2干活
			for (int i = 1; i <= 15; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i
						+ "\t总轮询次数：" + totalLoop);
			}
			// 3唤醒下一个线程
			number = 1;
			condition1.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

}

/**
 * 
 * @author admin 题目：三个线程，要求实现按序访问,A>B>C...... A打印5次，B打印10次，C打印15次 接着按照上述同样的顺序，再来
 *         A打印5次，B打印10次，C打印15次 接着按照上述同样的顺序，再来 。。。。。。来20轮 1 封装 2 线程 操作 资源 3
 *         高内聚+低耦合
 * 
 */
public class ThreadDemo4 {
	public static void main(String[] args) {
		final ShareResource sr = new ShareResource();

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 20; i++) {
					sr.LoopA(i);
				}
			}
		}, "BB").start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 20; i++) {
					sr.LoopB(i);
				}
			}
		}, "CC").start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 20; i++) {
					sr.LoopC(i);
					System.out.println();
				}
			}
		}, "AA").start();

	}
}
