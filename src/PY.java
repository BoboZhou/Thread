import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PY {
	Lock lock = new ReentrantLock();
	Condition c1 = lock.newCondition();
	Condition c2 = lock.newCondition();
	int count = 0;
	boolean printNum = true;

	public void printN(int num) {
		lock.lock();

		try {
			while (!printNum) {
				c1.await();
			}
			System.out.println(num);
			count++;
			if (count == 2) {
				count = 0;
				printNum = false;
				c2.signal();
				;
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			lock.unlock();
		}

	}

	public void printA(int num) {
		lock.lock();

		try {
			while (printNum) {
				c2.await();
			}
			System.out.println((char) ('A' + num));
			c1.signal();
			printNum = true;

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			lock.unlock();
		}

	}

	public static void main(String[] args) {
		final PY py = new PY();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 52; i++) {
					py.printN(i);
				}

			}
		}).start();
		
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 26; i++) {
					py.printA(i);
				}

			}
		}).start();
	}

}
