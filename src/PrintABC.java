import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写一个程序，开启3个线程，这3个线程的ID分别为A、B、C， 每个线程将自己的ID在屏幕上打印10遍，要求输出结果必须按ABC的顺序显示；
 * 如：ABCABC….依次递推
 * 
 * PrintABC 创建人:LBM 时间：2016年11月25日-上午9:34:53
 * 
 * @version 1.0.0
 * 
 */
public class PrintABC {
	public static void main(String[] args) {
		final PrintClass printClass = new PrintClass();
		new Thread(new Runnable() {
			@Override
			public void run() {// 线程A
				for (int i = 1; i <= 10; i++) {
					printClass.PrintNameA(i);
				}
			}
		}, "A").start();
		new Thread(new Runnable() {// 线程B
					@Override
					public void run() {
						for (int i = 1; i <= 10; i++) {
							printClass.PrintNameB(i);
						}
					}
				}, "B").start();
		new Thread(new Runnable() {// 线程C
					@Override
					public void run() {
						for (int i = 1; i <= 10; i++) {
							printClass.PrintNameC(i);
						}
					}
				}, "C").start();
	}

	static class PrintClass {
		private String flag = "A";
		Lock lock = new ReentrantLock();
		Condition conditionA = lock.newCondition();// 控制A线程运行
		Condition conditionB = lock.newCondition();// 控制B线程运行
		Condition conditionC = lock.newCondition();// 控制C线程运行

		public void PrintNameA(int i) {// 线程A的任务
			lock.lock();
			while (!"A".equals(flag)) {
				try {
					conditionA.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				System.out.println("A线程第" + i + "次输出---"
						+ Thread.currentThread().getName());
				flag = "B";
				conditionB.signal();
			} finally {
				lock.unlock();
			}
		}

		public void PrintNameB(int i) {// 线程B的任务
			lock.lock();
			while (!"B".equals(flag)) {
				try {
					conditionB.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				System.out.println("B线程第" + i + "次输出---"
						+ Thread.currentThread().getName());
				flag = "C";
				conditionC.signal();
			} finally {
				lock.unlock();
			}
		}

		public void PrintNameC(int i) {// 线程C的任务
			lock.lock();
			while (!"C".equals(flag)) {
				try {
					conditionC.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				System.out.println("C线程第" + i + "次输出---"
						+ Thread.currentThread().getName());
				flag = "A";
				conditionA.signal();
			} finally {
				lock.unlock();
			}
		}
	}
}