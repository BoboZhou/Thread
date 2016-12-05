import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T3 {

	private int a;
	Lock lock = new ReentrantLock();

	public synchronized void inCre() {

		try {
			while (a == 1) {
				this.wait();
			}
			a++;
			System.out.println(a);
			this.notifyAll();

		} catch (Exception e) {

		} finally {

		}
	}

	public synchronized void deCre() {

		try {
			while (a == 0) {
				this.wait();
			}

			a--;
			System.out.println(a);
			this.notifyAll();

		} catch (Exception e) {

		} finally {

		}
	}

	public static void main(String[] args) {
		final T3 t3 = new T3();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					t3.inCre();
				}

			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					t3.deCre();
				}

			}
		}).start();
	}

}
