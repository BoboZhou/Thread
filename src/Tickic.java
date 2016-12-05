public class Tickic {
	private int ticke = 30;

	public void run() {
		synchronized (this) {
			while (ticke < 0) {
				try {
					wait();
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + "剩余 :"
					+ ticke);
			ticke--;
			notifyAll();

		}

	}

	public static void main(String[] args) {
		final Tickic tickic = new Tickic();

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					tickic.run();
				}

			}
		},"2").start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					tickic.run();
				}

			}
		},"3").start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					tickic.run();
				}

			}
		},"1").start();

	}

}
