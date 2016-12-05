import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class Ress implements Callable<Integer> ,Runnable{
	private int k;
	

	@Override
	public void run() {
		k=4;
		
	}
	@Override
	public Integer call() throws Exception {
		
		Thread.sleep(400);
		return k;
	}

}

public class FutureTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
		Integer result = 0;
		final Ress ress = new Ress();
		FutureTask<Integer> futureTask = new FutureTask<>(ress,result);
		try {
			System.out.println(futureTask.get(4,TimeUnit.SECONDS));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		new Thread(futureTask).start();
		System.out.println(futureTask.get());
	}
}
