import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @包名
 * @类名 T4.java
 * @作者 Bobo
 * @创建日期 2016年12月2日下午6:07:22
 * @描述
 * @版本 V 1.0 写两个线程 写两个线程 一个打印1-52 一个打印 A--Z 1 2 A 3 4
 */
class Resource {
	Lock lock = new ReentrantLock();
	private int num = 1;
	private char ch = 65;
	private boolean Numfalg = true;
	Condition cN = lock.newCondition();
	Condition cA = lock.newCondition();

	public void sysNum(int k) throws InterruptedException {
		lock.lock();
		try {
			while (!Numfalg) {
				cN.await();
			}
			
				System.out.println(2*k-1);
				System.out.println(2*k);
			
			Numfalg = false;
			cA.signal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void sysA(int k) {
		lock.lock();
		try {

			while (Numfalg) {
				cA.await();
			}
			
				System.out.println((char)(ch+k));
			
			Numfalg = true;
			cN.signal();
		} catch (Exception e) {

		} finally {
			lock.unlock();
		}
	}
}

public class T4 {
	// 线程操作资源
	// 高内聚 低耦合
	public static void main(String[] args) {
	
		final Resource t4 = new Resource();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 26; i++) {
					try {
						t4.sysNum(i);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}

			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 26; i++) {
					try {
						t4.sysA(i);
					} catch (Exception e) {

						e.printStackTrace();
					}
				}

			}
		}).start();
	}

}
